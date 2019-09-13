package com.ccb.domain.utils;

import com.ccb.domain.dao.domain.DomainPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class QueryDomainByName {

    @Autowired
    private EntityManager entityManager;

    public DomainPO getDomainByname(String domainName){
        // 1. 利用entityManager构建出CriteriaQuery类型的参数
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DomainPO> query = builder.createQuery(DomainPO.class);
        // 2. 获取User的Root，也就是包装对象
        Root<DomainPO> root = query.from(DomainPO.class);
        // 3. 构建查询条件，这里相当于where user.id = id;
        Predicate predicate = builder.and(
                builder.equal(root.get("domainName").as(String.class), domainName)
        );
        query.where(predicate); // 到这里一个完整的动态查询就构建完成了
        // 指定查询结果集，相当于“select id，name...”，如果不设置，默认查询root中所有字段
        query.select(root);
        // 4. 执行查询,获取查询结果
        TypedQuery<DomainPO> typeQuery = entityManager.createQuery(query);
        List<DomainPO> resultList = typeQuery.getResultList();
        // System.out.println("执行查询返回的结果"+resultList);
        DomainPO dp =null;
        if (resultList.size()>0){
            dp= resultList.get(0);
        }

        return dp;
    }

}
