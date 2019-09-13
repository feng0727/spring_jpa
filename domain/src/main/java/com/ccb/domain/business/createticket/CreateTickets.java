package com.ccb.domain.business.createticket;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ccb.domain.dao.domain.DomainPO;
import com.ccb.domain.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class CreateTickets {

    public  String createticket(DomainPO domainPO){
        //创建工单  TODO
        String createUrl="https://itsm.uyun.cn/openapi/v2/tickets/create?model_id=&user_id=&email=&apikey=";
        JSONObject header=new JSONObject();
        // todo
        header.put("model_id","model_id");
        header.put("user_id","user_id");
        header.put("apikey","apikey");

        JSONObject bodyParam=new JSONObject();
        JSONObject formParam=new JSONObject();
        //表单内容 字段code与值对应(例子中几项为必填)  TODO
        formParam.put("title","");
        formParam.put("urgentLevel","");
        formParam.put("ticketDesc","");
        bodyParam.put("form",formParam);
        //指定下一环节处理人
        JSONArray user_list=new JSONArray();
        user_list.add("环节处理人的名字");
        //指定下一环节用户组
        JSONArray group_list=new JSONArray();
        //指定环节处理人
        JSONObject executors=new JSONObject();
        // 指定用户组时需要用这个属性
        JSONObject executors_group=new JSONObject();
        //配置项id
        bodyParam.put("source_id","");
        //工单来源
        bodyParam.put("ticket_source","工单来源");
        //处理意见
        JSONObject messageParam=new JSONObject();
        messageParam.put("to_user_list","");
        messageParam.put("content","");
        bodyParam.put("message",messageParam);
        // 创建者所在部门，开启组织机构过滤功能时必须传
        bodyParam.put("depart_id","");
        //并行环节指定人员需要额外指定
        JSONObject parallelism_tache_userParam=new JSONObject();
        JSONArray valArray1=new JSONArray();
        valArray1.add("user1");
        JSONArray valArray2=new JSONArray();
        valArray2.add("user2");
        parallelism_tache_userParam.put("2.1",valArray1);
        parallelism_tache_userParam.put("2.2",valArray2);
        bodyParam.put("parallelism_tache_user",parallelism_tache_userParam);
        //并行环节指定用户组需要额外指定
        JSONObject parallelism_tache_groupParam=new JSONObject();
        JSONArray groupArray1=new JSONArray();
        groupArray1.add("user1");
        JSONArray groupArray2=new JSONArray();
        groupArray2.add("user2");
        parallelism_tache_groupParam.put("2.1",valArray1);
        parallelism_tache_groupParam.put("2.2",valArray2);
        bodyParam.put("parallelism_tache_group",parallelism_tache_groupParam);

        Map<String, String> map = HttpClientUtils.post(createUrl, header, bodyParam, 100);
        log.info("调用创建工单返回的状态值"+map.get("status"));
        return "调用创建工单返回的状态值: "+map.get("status");

    }

}
