package com.squapl.stark.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Data
@Table(name = "service_details")
public class ServiceDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private Service service;


    private int sessions;
    private int validity;

    private BigDecimal base_grossfee;
    private BigDecimal base_tax;
    private int base_nettotal;
    private int graceperiod;

    private String isactive;

    private long createdby;
    private Date createddatetime;
    private long updatedby;
    private Date updateddatetime;


}



