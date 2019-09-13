package com.ccb.domain.business.domainhistory;


import com.ccb.domain.dao.domain.DomainPO;
import com.ccb.domain.dao.domainhistory.DomainHistoryPO;
import com.ccb.domain.frontapi.vo.DomainHistoryVO;
import com.ccb.domain.frontapi.vo.DomainVO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: wangyiheng
 * @Date: 2019/8/22 Time: 12:13
 * @desc 历史域名信息接口
 */
@Component
public interface DomainHistoryBusiness {

      public DomainHistoryVO insertHistoryDomain(DomainHistoryVO domainHistoryVO);

    /*
       通过条件筛选
     */
   // public Page<DomainHistoryPO> filterHistoryDomain(int pageNum, int pageSize, DomainHistoryVO domainHistoryVO);

     //
    Page<DomainHistoryPO> filterDomainHis(Integer pageNum, Integer pageSize, String name, String startTime, String endTime);
}
