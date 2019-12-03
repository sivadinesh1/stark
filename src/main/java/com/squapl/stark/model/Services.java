package com.squapl.stark.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "service")
public class Services extends Auditable<String> {
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_sub_category_id")
    private ServiceSubCategory servicesubcategory;


    private String isactive;
    private int units_in_stock;

    @Transient
    private String selectedcatid;
    @Transient
    private String selectedsubcatid;

    private int sessions;
    private int validity;

    private BigDecimal base_grossfee;
    private BigDecimal base_tax;
    private int base_nettotal;
    private int graceperiod;


}



