package com.ccb.domain.dao.relation;

import com.ccb.domain.dao.domain.DomainPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RelationDao extends JpaRepository<RelationPO, Integer>, JpaSpecificationExecutor<RelationPO> {
}
