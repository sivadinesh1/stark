package com.squapl.stark.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "enquiry")
public class Enquiry extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "center_id")
    private Center center;

    private String firstname;
    private String lastname;
    private String mobilenumber;
    private String email;
    private String gender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private Services service;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_category_id")
    private ServiceCategory servicecategory;


    private String lead_source;
    private String notes;
    private Date enq_start;
    private Date enq_close;

    private String status;


}



