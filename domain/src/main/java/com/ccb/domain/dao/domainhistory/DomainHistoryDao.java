package com.ccb.domain.dao.domainhistory;

import com.ccb.domain.dao.domain.DomainPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainHistoryDao extends JpaRepository<DomainHistoryPO, Integer>, JpaSpecificationExecutor<DomainHistoryPO> {
}
