package com.ccb.domain.dao.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DomainFindone extends JpaRepository<DomainPO, String> {


    @Query("select dp.id,dp.domainName,dp.applicationDate,dp.ccbOtherAccess,dp.ccbOtherAnalysis,dp.ccbOtherIp,dp.createTime,dp.creator,dp.eccAccess,dp.eccIfAnalysis,dp.eccIp,dp.ifCertificate from DomainPO dp where  dp.domainName='测试001'")
    List<DomainPO> findDomainPOByName(@Param("domainName")String domainName);

}
