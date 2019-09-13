package com.ccb.domain.business.domain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ccb.domain.business.createticket.CreateTickets;
import com.ccb.domain.business.domainhistory.DomainHistoryBusinessImpl;
import com.ccb.domain.common.i18n.I18nMessages;
import com.ccb.domain.dao.domain.DomainDao;
import com.ccb.domain.dao.domain.DomainFindone;
import com.ccb.domain.dao.domain.DomainPO;
import com.ccb.domain.exception.DemoException;
import com.ccb.domain.frontapi.HistoryDomainFrontApi;
import com.ccb.domain.frontapi.vo.DomainHistoryVO;
import com.ccb.domain.frontapi.vo.DomainVO;
import com.ccb.domain.utils.HttpClientUtils;
import com.ccb.domain.utils.QueryDomainByName;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uyun.springboot.consumer.common.ApiSupport;
import uyun.springboot.consumer.common.RequestParamsHolder;
import uyun.whale.security.acl.AclServices;
import uyun.whale.security.acl.api.entity.AclEntry;
import uyun.whale.security.acl.api.entity.Subject;
import uyun.whale.security.acl.core.DefaultAclAction;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: wangyiheng
 * @desc: 域名信息
 * @date: created in 2019/8/22 09:55
 * @modifed by:
 */
@Slf4j
@Service
public class DomainBusinessImpl implements DomainBusiness {

    @Autowired
    private DomainDao domainDao;

    @Autowired
    private DomainFindone domainFindone;

    @Autowired
    private DomainHistoryBusinessImpl domainHistoryBusinessImpl;

    @Autowired
    private QueryDomainByName createQuery;

    @Autowired
    private CreateTickets createTickets;

    @Override
    public DomainPO insertAndCreateTicket(DomainPO domainPO) {
        //先把提交的数据保存到数据库中
        domainPO.setRecord("未开始");
        //入库
        DomainPO dop = domainDao.save(domainPO);

        String result = createTickets.createticket(domainPO);
        log.info(result);

        // 创建者拥有删除权限
        log.debug("add 'DELETE' acl entry for object '{}' of type: {}", domainPO.getId(), domainPO.getClass().getName());
        String subjectId = RequestParamsHolder.getParameter(ApiSupport.KEY_USER_ID);
        AclServices.getAclEntryService().addEntries(Arrays.asList(
                new AclEntry(String.valueOf(domainPO.getId()), DomainPO.class.getName(), DefaultAclAction.DELETE, subjectId, Subject.SubjectTypes.USER.name())
        ));
        return dop;
    }
    @Override
    public List<DomainPO> insertBatchDomain(List<DomainPO> domainPO) {
        return domainDao.saveAll(domainPO);
    }

    @Override
    public void deleteDomainAndInhis(DomainPO domainPO) {

        // 判断是否有删除权限
        String userId = RequestParamsHolder.getParameter(ApiSupport.KEY_USER_ID);
        String action = DefaultAclAction.DELETE.name();
        if (!AclServices.hasPermission(userId, DomainPO.class, String.valueOf(domainPO.getId()), action)) {
            throw DemoException.createClientException(I18nMessages.ERROR_NOT_AUTHORIZED, domainPO.getId(), action);
        }

        domainDao.deleteById(domainPO.getId());
        //撤销之后入历史库
        //存入历史库
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date  date=new Date();
        String createTime = sf.format(date);

        domainPO.setRecord("已撤销");
        domainPO.setId(null);
        DomainHistoryVO domainHistoryVO = new DomainHistoryVO();
        domainHistoryVO.setCreateTime(createTime);
        BeanUtils.copyProperties(domainPO,domainHistoryVO);
        domainHistoryBusinessImpl.insertHistoryDomain(domainHistoryVO);

        // 对象删除，关联的ACL记录也要同时删除
        log.debug("delete acl entries for object '{}' of type: {}", domainPO.getId(), domainPO.getClass().getName());
        AclServices.getAclEntryService().deleteEntriesByObject(domainPO.getClass().getName(), String.valueOf(domainPO.getId()));
    }

    @Override
    public void deleteDomain(DomainPO domainPO) {

        // 判断是否有删除权限
        String userId = RequestParamsHolder.getParameter(ApiSupport.KEY_USER_ID);
        String action = DefaultAclAction.DELETE.name();
        if (!AclServices.hasPermission(userId, DomainPO.class, String.valueOf(domainPO.getId()), action)) {
            throw DemoException.createClientException(I18nMessages.ERROR_NOT_AUTHORIZED, domainPO.getId(), action);
        }

        domainDao.deleteById(domainPO.getId());

        // 对象删除，关联的ACL记录也要同时删除
        log.debug("delete acl entries for object '{}' of type: {}", domainPO.getId(), domainPO.getClass().getName());
        AclServices.getAclEntryService().deleteEntriesByObject(domainPO.getClass().getName(), String.valueOf(domainPO.getId()));
    }


     //修改数据 并把元数据存入历史库中
    @Override
    public DomainPO updateDomainAndInHis(DomainPO domainPO) {
        System.out.println("修改的数据"+domainPO);
        DomainPO td = domainDao.findById(domainPO.getId()).orElse(null);
        if (td != null) {
            domainDao.save(domainPO);
            //存入历史库
            domainPO.setRecord("已修改");
            SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date  date=new Date();
            String createTime = sf.format(date);

            domainPO.setId(null);
            DomainHistoryVO domainHistoryVO = new DomainHistoryVO();
            domainHistoryVO.setCreateTime(createTime);
            BeanUtils.copyProperties(domainPO,domainHistoryVO);
            domainHistoryBusinessImpl.insertHistoryDomain(domainHistoryVO);
        }
        return domainPO;
    }

    //修改数据
    @Override
    public DomainPO updateDomain(DomainPO domainPO) {
        System.out.println("修改的数据"+domainPO);
        DomainPO td = domainDao.findById(domainPO.getId()).orElse(null);
        if (td != null) {
            domainDao.save(domainPO);
        }
        return domainPO;
    }


    //查询服务
    @Override
    public Page<DomainPO> selectDomainFindAll(Integer pageNum, Integer pageSize) {
        //return domainDao.findAll();
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return  domainDao.findAll(pageable);
    }


    @Override
    public Page<DomainPO> filterDomain(Integer pageNum, Integer pageSize, String name,String startTime,String endTime) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);

       /* Page<DomainPO> uList = domainDao.findAll(new Specification<DomainPO>() {
            @Override
            public Predicate toPredicate(Root<DomainPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (domainVO.getCreator() != null && !domainVO.getCreator().equals("")) {
                    predicates.add(cb.like(root.get("creator").as(String.class), "%" + domainVO.getCreator() + "%"));
                }
                 if (domainVO.getDomainName() != null && !domainVO.getDomainName().equals("")) {
                     predicates.add(cb.like(root.get("domain_name").as(String.class), "%" + domainVO.getDomainName() + "%"));

                }
                 if (domainVO.getEmail() != null && !domainVO.getEmail().equals("")) {
                    predicates.add(cb.like(root.get("email").as(String.class), "%" + domainVO.getEmail() + "%"));
                }
                 if (domainVO.getPhone() != null && !domainVO.getPhone().equals("")) {
                    predicates.add(cb.like(root.get("phone").as(String.class), "%" + domainVO.getPhone() + "%"));
                }
                 if (domainVO.getLinkman() != null && !domainVO.getLinkman().equals("")) {
                    predicates.add(cb.like(root.get("linkman").as(String.class), "%" + domainVO.getLinkman() + "%"));
                }
                 if (domainVO.getStartTime() != null && !domainVO.getStartTime().equals("") && domainVO.getEndTime() != null && !domainVO.getEndTime().equals("")) {
                    predicates.add(cb.between(root.get("create_time").as(Long.class),Long.valueOf(domainVO.getStartTime()),Long.valueOf(domainVO.getEndTime())));
                }
                Predicate[] pre = new Predicate[predicates.size()];
                criteriaQuery.where(predicates.toArray(pre));
                return cb.and(predicates.toArray(pre));
            }
        }, pageable);*/
        System.out.println("name:"+name);
        System.out.println("startTime:"+startTime);
        System.out.println("endTime:"+endTime);

        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sf.parse(startTime);
            endDate = sf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long startDateTime = startDate.getTime();
        Long endDateTime = endDate.getTime();

        Page<DomainPO> uList = domainDao.findAll(new Specification<DomainPO>() {
            @Override
            public Predicate toPredicate(Root<DomainPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(name)) {
                    predicates.add(cb.like(root.get("domainName").as(String.class), "%" + name + "%"));
                }
                if (startTime != null && !startTime.trim().equals("")) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("applicationDate").as(String.class), startTime));
                }

                if (endTime != null && !endTime.trim().equals("")) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("applicationDate").as(String.class), endTime));
                }

                else if (!StringUtils.isEmpty(name)) {
                    predicates.add(cb.like(root.get("linkman").as(String.class), "%" + name + "%"));
                }
                /*if (startTime!=null) {
                    predicates.add(cb.between(root.get("createTime").as(Long.class),startDateTime,endDateTime));
                }*/


                Predicate[] pre = new Predicate[predicates.size()];
                criteriaQuery.where(predicates.toArray(pre));
                return cb.and(predicates.toArray(pre));
            }
        }, pageable);

        return uList;

      //  return uList;
    }

        //上传  file 要上传的文件
        @Override
        public  String upload(MultipartFile file, HttpServletRequest request){
            //通过上下文获取服务器绝对路径  todo
           // String path = request.getSession().getServletContext().getRealPath("upload");
            String path ="/opt/xxx";
            //生成一个新文件名称
            String filename= UUID.randomUUID().toString()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            //实例化一个文件对象
            File file2=new File(path+File.separator+filename);
            if(!file2.exists()){
                file2.mkdirs();

            }
            try {
                file.transferTo(file2);
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String filePath="upload/"+filename;

            return filePath;
    }


    //下载
    @Override
    public void downloadDataDevFile(HttpServletRequest request, HttpServletResponse response, String filename, String fileStorpath){

        String filePath = fileStorpath;
        try {
            filePath = new String(filePath.getBytes("ISO-8859-1"),"UTF-8");
            String rootPath = request.getSession().getServletContext().getRealPath("/");
            //路径
            File file = new File(rootPath+filePath);
            String downFileName = file.getName();

            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(downFileName.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateRecodTodoing(String domainName) {

        DomainPO dp = createQuery.getDomainByname(domainName);
        dp.setRecord("进行中");
        domainDao.save(dp);

    }

    @Override
    public void updateRecodToDone(String domainName) {
        DomainPO dp= createQuery.getDomainByname(domainName);
        dp.setRecord("已完成");
        domainDao.save(dp);


    }

    @Override
    public Page<DomainPO> findAllDescsort(Integer pageNum, Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        Pageable pageable = PageRequest.of(pageNum, pageSize,sort);
        return  domainDao.findAll(pageable);
    }

    @Override
    public Page<DomainPO> queryDomainAscSort(Integer pageNum, Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.ASC,"createTime");
        Pageable pageable = PageRequest.of(pageNum, pageSize,sort);
        return  domainDao.findAll(pageable);
    }

    @Override
    public Page<DomainPO> queryDomainscSortByname(Integer pageNum, Integer pageSize) {
        Sort sort = new Sort(Sort.DEFAULT_DIRECTION,"domainName");
        Pageable pageable = PageRequest.of(pageNum, pageSize,sort);
        return  domainDao.findAll(pageable);
    }

    @Override
    public void cancleDomain(DomainVO domainVO) {
        DomainPO td = domainDao.findById(domainVO.getId()).orElse(null);
     /*   System.out.println("这是td: "+td);

        DomainPO domainPO = new DomainPO();
        domainPO.setUpdateTime(new Date().getTime());
        BeanUtils.copyProperties(domainVO,domainPO);

        DomainPO  domainPO1=new DomainPO();*/

         SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date  date=new Date();
        String createTime = sf.format(date);
        DomainVO domainVO1 = new DomainVO();
        DomainPO  domainPOnew=new DomainPO();
        domainVO1.setInterIp(null);
        domainVO1.setEverestIp(null);
        domainVO1.setEccIp(null);
        domainVO1.setVpcIp(null);
        domainVO1.setCcbOtherIp(null);
        domainPOnew.setCreateTime(createTime);
        domainVO1.setId(td.getId());
        domainVO1.setDomainName(td.getDomainName());
        domainVO1.setRecord(td.getRecord());
        domainVO1.setSysService(td.getSysService());
        domainVO1.setPort(td.getPort());
        domainPOnew.setIfCertificate(td.getIfCertificate());


        BeanUtils.copyProperties(domainVO1,domainPOnew);

        System.out.println("餐数: "+domainPOnew);

        if (domainPOnew != null) {
            domainDao.save(domainPOnew);
            //存入历史库
        domainPOnew.setRecord("已修改");

                        domainPOnew.setId(null);
                        DomainHistoryVO domainHistoryVO = new DomainHistoryVO();

                        domainHistoryVO.setCreateTime(createTime);
                        BeanUtils.copyProperties(domainPOnew,domainHistoryVO);
                        domainHistoryBusinessImpl.insertHistoryDomain(domainHistoryVO);

        }

    }

    //新增
    public DomainPO insertDomain(DomainPO domainPO) {
        //入库
        DomainPO dop = domainDao.save(domainPO);
        return dop;
    }

    //创建工单
    public  String createTicket(DomainPO domainPO){
        String s = createTickets.createticket(domainPO);
        return  s;
    }

    //把修改的提交历史库
    public void summaryHisUpdDomain(String domainName){
        DomainPO domainPO = createQuery.getDomainByname(domainName);
        domainPO.setRecord("已修改");
        domainPO.setId(null);
        DomainHistoryVO domainHistoryVO = new DomainHistoryVO();

        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date  date=new Date();
        String createTime = sf.format(date);
         domainHistoryVO.setCreateTime(createTime);
        BeanUtils.copyProperties(domainPO,domainHistoryVO);
        domainHistoryBusinessImpl.insertHistoryDomain(domainHistoryVO);

    }

    //把撤销的提交历史库
    public void summaryHisCelDomain(String domainName){
        DomainPO domainPO = createQuery.getDomainByname(domainName);
        domainPO.setRecord("已撤销");
        domainPO.setId(null);
        DomainHistoryVO domainHistoryVO = new DomainHistoryVO();

        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date  date=new Date();
        String createTime = sf.format(date);
        domainHistoryVO.setCreateTime(createTime);
        BeanUtils.copyProperties(domainPO,domainHistoryVO);
        domainHistoryBusinessImpl.insertHistoryDomain(domainHistoryVO);

    }

}
