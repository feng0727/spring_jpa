package com.ccb.domain.dao.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainDao extends JpaRepository<DomainPO, Integer>, JpaSpecificationExecutor<DomainPO> {



}
