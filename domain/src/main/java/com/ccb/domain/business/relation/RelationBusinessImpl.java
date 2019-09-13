package com.ccb.domain.business.relation;

import com.ccb.domain.common.i18n.I18nMessages;
import com.ccb.domain.dao.domain.DomainDao;
import com.ccb.domain.dao.domain.DomainPO;
import com.ccb.domain.dao.relation.RelationDao;
import com.ccb.domain.dao.relation.RelationPO;
import com.ccb.domain.exception.DemoException;
import com.ccb.domain.frontapi.vo.RelationVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uyun.springboot.consumer.common.ApiSupport;
import uyun.springboot.consumer.common.RequestParamsHolder;
import uyun.whale.security.acl.AclServices;
import uyun.whale.security.acl.core.DefaultAclAction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class RelationBusinessImpl implements  RelationBusiness{

    @Autowired
    private RelationDao relationDao;

    @Autowired
    private DomainDao domainDao;

    /*
        域名解析  筛选查询
     */
    @Override
    public Page<RelationPO> filterHistoryDomain(int pageNum, int pageSize, RelationVO relationVO) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<RelationPO> uList = relationDao.findAll(new Specification<RelationPO>() {
            @Override
            public Predicate toPredicate(Root<RelationPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (relationVO.getTickedCreator() != null && !relationVO.getTickedCreator().equals("")) {
                    predicates.add(cb.like(root.get("creator").as(String.class), "%" + relationVO.getTickedCreator()+ "%"));
                }
                if (relationVO.getTicketState()!= null && !relationVO.getTicketState().equals("")) {
                    predicates.add(cb.like(root.get("domain_name").as(String.class), "%" + relationVO.getTicketState() + "%"));
                }
                if (relationVO.getDomainId()!= null && !relationVO.getDomainId().equals("")) {
                    predicates.add(cb.like(root.get("email").as(String.class), "%" + relationVO.getDomainId() + "%"));
                }

                Predicate[] pre = new Predicate[predicates.size()];
                criteriaQuery.where(predicates.toArray(pre));
                return cb.and(predicates.toArray(pre));
            }
        }, pageable);

        return uList;
    }

    //修改
    @Override
    public void updateRelation(RelationPO relationPO) {
        RelationPO td = relationDao.findById(relationPO.getId()).orElse(null);
        if (td != null) {
            relationDao.save(relationPO);
        }

    }

    //删除
    @Override
    public  void deleteRelation(RelationPO relationPO) {
        // 判断是否有删除权限
        String userId = RequestParamsHolder.getParameter(ApiSupport.KEY_USER_ID);
        String action = DefaultAclAction.DELETE.name();
        if (!AclServices.hasPermission(userId, DomainPO.class, String.valueOf(relationPO.getId()), action)) {
            throw DemoException.createClientException(I18nMessages.ERROR_NOT_AUTHORIZED, relationPO.getId(), action);
        }

        relationDao.deleteById(relationPO.getId());

        // 对象删除，关联的ACL记录也要同时删除
        log.debug("delete acl entries for object '{}' of type: {}", relationPO.getId(), relationPO.getClass().getName());
        AclServices.getAclEntryService().deleteEntriesByObject(relationPO.getClass().getName(), String.valueOf(relationPO.getId()));
    }


    /**
     *    多条件筛选
     * @param pageNum
     * @param pageSize
     * @param name
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Page<DomainPO> filterDomainRelation(Integer pageNum, Integer pageSize, String name, String startTime, String endTime) {

        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<DomainPO> uList = domainDao.findAll(new Specification<DomainPO>() {
            @Override
            public Predicate toPredicate(Root<DomainPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(name)) {
                    predicates.add(cb.like(root.get("domainName").as(String.class), "%" + name + "%"));
                }
                //  todo
                if (startTime != null && !startTime.trim().equals("")) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("applicationDate").as(String.class), startTime));
                }

                if (endTime != null && !endTime.trim().equals("")) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("applicationDate").as(String.class), endTime));
                }

                else if (!StringUtils.isEmpty(name)) {
                    predicates.add(cb.like(root.get("linkman").as(String.class), "%" + name + "%"));
                }

                Predicate[] pre = new Predicate[predicates.size()];
                criteriaQuery.where(predicates.toArray(pre));
                return cb.and(predicates.toArray(pre));
            }
        }, pageable);

        return uList;
    }


}
