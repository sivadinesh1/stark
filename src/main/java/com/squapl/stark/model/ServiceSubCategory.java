package com.squapl.stark.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;


@Entity
@DynamicUpdate
@Data
@Table(name = "service_sub_category")
public class ServiceSubCategory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "center_id")
    private Center center;

    private String name;
    private String description;
    private String isactive;

    private Long createdby;
    private Date createddatetime;
    private Long updatedby;
    private Date updateddatetime;


}




