package com.ccb.domain.business.domain;

import com.ccb.domain.dao.domain.DomainPO;
import com.ccb.domain.frontapi.vo.DomainVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author: wangyiheng
 * @Date: 2019/8/22 Time: 09:52
 * @desc 域名信息接口
 */
public interface DomainBusiness {
    /**
     * param DomainPO
     * return
     * desc 新增域名信息并且创建工单
     */
    public DomainPO insertAndCreateTicket(DomainPO domainPO);

    /**
     *  新增域名
     */
    public DomainPO insertDomain(DomainPO domainPO);
    /**
     *  创建工单
     */
    public String createTicket(DomainPO domainPO);

    /**
     * param DomainPO
     * return
     * desc 批量新增域名信息
     */
    public List<DomainPO> insertBatchDomain(List<DomainPO> domainPO);

    /**
     * param DomainPO
     * return
     * desc 删除域名信息并入历史库
     */
    public void deleteDomainAndInhis(DomainPO domainPO);

    /**
     *
     * 删除信息
     */
    public void deleteDomain(DomainPO domainPO);

    /**
     * param DomainPO
     * return
     * desc 更新域名信息
     */
    public DomainPO updateDomainAndInHis(DomainPO domainPO);

    /**
     *
     *        查询所有任务信息
     *
     **/

   public Page<DomainPO> selectDomainFindAll(Integer pageNum, Integer pageSize);


    /**
     * param DomainPO
     * return
     * desc 筛选域名信息
     */
    public Page<DomainPO> filterDomain(Integer pageNum, Integer pageSize, String  name, String startTime,String endTime);


    /**
     * 上传
     */
    public String upload(MultipartFile file, HttpServletRequest request);


    /**
     * 下载
     * @param filename 文件名称
     * @param  fileStorpath 文件的真实路径
     **/
    public void downloadDataDevFile(HttpServletRequest request, HttpServletResponse response, String filename, String fileStorpath);


    //工单处理之后  修改装备案状态的值为进行中
    public void  updateRecodTodoing(String domainName);

    //工单处理之后  修改装备案状态的值为已完成
    public void  updateRecodToDone(String domainName);

    //
    public Page<DomainPO> findAllDescsort(Integer pageNum, Integer pageSize);

    public Page<DomainPO> queryDomainAscSort(Integer pageNum, Integer pageSize);

    public Page<DomainPO> queryDomainscSortByname(Integer pageNum, Integer pageSize);

    //域名解析撤肖
    void cancleDomain(DomainVO domainVO);

    //把修改的提交历史库
    public void summaryHisUpdDomain(String domainName);

    //把撤销的提交历史库
    public void summaryHisCelDomain(String domainName);

    public DomainPO updateDomain(DomainPO domainPO);

}