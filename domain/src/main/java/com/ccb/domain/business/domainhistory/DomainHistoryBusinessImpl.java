package com.ccb.domain.business.domainhistory;

import com.ccb.domain.dao.domain.DomainPO;
import com.ccb.domain.dao.domainhistory.DomainHistoryDao;
import com.ccb.domain.dao.domainhistory.DomainHistoryPO;
import com.ccb.domain.frontapi.vo.DomainHistoryVO;
import com.ccb.domain.frontapi.vo.DomainVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DomainHistoryBusinessImpl implements  DomainHistoryBusiness{

     @Autowired
     private DomainHistoryDao domainHistoryDao;

     //把数据家加到历史库中
    @Override
    public DomainHistoryVO insertHistoryDomain(DomainHistoryVO domainHistoryVO) {
        DomainHistoryPO  domainHistoryPO=new DomainHistoryPO();
        BeanUtils.copyProperties(domainHistoryVO,domainHistoryPO);
        domainHistoryDao.save(domainHistoryPO);
        return domainHistoryVO;
    }

    /*
            条件查询  history的
         */



    @Override
    public Page<DomainHistoryPO> filterDomainHis(Integer pageNum, Integer pageSize, String name, String startTime, String endTime) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);

        Page<DomainHistoryPO> uList = domainHistoryDao.findAll(new Specification<DomainHistoryPO>() {
            @Override
            public Predicate toPredicate(Root<DomainHistoryPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
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
