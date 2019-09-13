package com.ccb.domain.dao.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "domain_info_table")
@Data
public class DomainPO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "domain_name")
    private String domainName;

    private String record;

    @Column(name = "sys_service")
    private String sysService;

    private String port;

    @Column(name = "if_certificate")
    private Boolean ifCertificate;

    private String owner;

    private String linkman;

    private String email;

    private String phone;

    @Column(name = "inter_access")
    private Boolean interAccess;

    @Column(name = "inter_ip")
    private String interIp;

    @Column(name = "inter_if_analysis")
    private Boolean interIfAnalysis;

    @Column(name = "everest_access")
    private Boolean everestAccess;

    @Column(name = "everest_ip")
    private String everestIp;

    @Column(name = "everest_if_analysis")
    private Boolean everestIfAnalysis;

    @Column(name = "ecc_access")
    private Boolean eccAccess;

    @Column(name = "ecc_ip")
    private String eccIp;

    @Column(name = "ecc_if_analysis")
    private Boolean eccIfAnalysis;

    @Column(name = "vpc_access")
    private Boolean vpcAccess;

    @Column(name = "vpc_ip")
    private String vpcIp;

    @Column(name = "vpc_if_analysis")
    private Boolean vpcIfAnalysis;

    @Column(name = "ccb_other_access")
    private Boolean ccbOtherAccess;

    @Column(name = "ccb_other_ip")
    private String ccbOtherIp;

    @Column(name = "ccb_other_analysis")
    private Boolean ccbOtherAnalysis;

    private String property;

    @Column(name = "application_date")
    private Long applicationDate;

    private String remark;

    private String creator;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "update_time")
    private Long updateTime;

    //备案情况
    @Column(name = "server_recording")
    private String server_recording;
}
