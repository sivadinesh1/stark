package com.squapl.stark.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "member_services")
public class MemberServices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User custuser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private User trainuser;


    private BigDecimal grossfee;
    private BigDecimal per_unit_fee;
    private BigDecimal tax;
    private int nettotal;
    private int discount;
    private String isactive;

    private String pymt_received;
    private int completed_sessions;
    private Date startdate;
    private Date enddate;

    private long createdby;
    private Date createddatetime;
    private long updatedby;
    private Date updateddatetime;

}

