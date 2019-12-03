package com.squapl.stark.model;

import javax.persistence.*;


@Entity

@Table(name = "category_sub_category")
public class CategorySubCategory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private ServiceCategory serviceCategory;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sub_category_id")
    private ServiceSubCategory serviceSubCategory;

    public CategorySubCategory(ServiceCategory sc, ServiceSubCategory ssc) {
        this.serviceCategory = sc;
        this.serviceSubCategory = ssc;
    }

    public CategorySubCategory() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ServiceCategory getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(ServiceCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public ServiceSubCategory getServiceSubCategory() {
        return serviceSubCategory;
    }

    public void setServiceSubCategory(ServiceSubCategory serviceSubCategory) {
        this.serviceSubCategory = serviceSubCategory;
    }

}

