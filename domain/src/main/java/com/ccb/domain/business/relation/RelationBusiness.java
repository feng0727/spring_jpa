package com.ccb.domain.business.relation;

import com.ccb.domain.dao.domain.DomainPO;
import com.ccb.domain.dao.relation.RelationPO;
import com.ccb.domain.frontapi.vo.RelationVO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public interface RelationBusiness {

    /*
        条件筛选  域名解析
     */

    public Page<RelationPO> filterHistoryDomain(int pageNum, int pageSize, RelationVO relationVO);

    /**
     * param RelationPO
     * return
     * desc 更新解析信息
     */
    public void updateRelation(RelationPO relationPO);

    /**
     * param RelationPO
     * return
     * desc 删除域名信息
     */
    public void deleteRelation(RelationPO relationPO);

    /**
     * 条件筛选
     */
    public  Page<DomainPO> filterDomainRelation(Integer pageNum, Integer pageSize, String name,String startTime,String endTime);



}
