package com.squapl.stark.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "center_id")
    private Center center;

    private String name;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_category_id")
    private ServiceCategory servicecategory;


    private String isactive;
    private int units_in_stock;


    private long createdby;
    private Date createddatetime;
    private long updatedby;
    private Date updateddatetime;
}



