package com.ccb.domain.dao.relation;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "relation_table")
@Data
public class RelationPO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "domain_id")
    private Integer domainId;

    @Column(name = "ticked_id")
    private String tickedId;

    @Column(name = "ticked_creator")
    private String tickedCreator;

    @Column(name = "create_time")
    private Long createTime;

    @Column(name = "update_time")
    private Long updateTime;

    @Column(name = "ticket_state")
    private String ticketState;    
}
