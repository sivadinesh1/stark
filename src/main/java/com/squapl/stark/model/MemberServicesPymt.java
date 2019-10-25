package com.squapl.stark.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Table(name = "member_services_pymt")
public class MemberServicesPymt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "msid")
    private MemberServices memberServices;


    private int actual_paid;
    private BigDecimal tax;
    private int gross;
    private int bal_to_pay;
    private Date paid_date;

    private long createdby;
    private Date createddatetime;
    private long updatedby;
    private Date updateddatetime;
}
