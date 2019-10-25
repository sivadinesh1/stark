package com.squapl.stark.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@DynamicUpdate

@Table(name = "service_category")
public class ServiceCategory implements Serializable {

    private static final long serialVersionUID = 1L;

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
    @OneToMany(mappedBy = "serviceCategory", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<CategorySubCategory> categorySubCategories = new HashSet<>();

    @Transient
    private String selectedsubcatids;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSelectedsubcatids() {
        return selectedsubcatids;
    }

    public void setSelectedsubcatids(String selectedsubcatids) {
        this.selectedsubcatids = selectedsubcatids;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public Long getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Long createdby) {
        this.createdby = createdby;
    }

    public Date getCreateddatetime() {
        return createddatetime;
    }

    public void setCreateddatetime(Date createddatetime) {
        this.createddatetime = createddatetime;
    }

    public Long getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(Long updatedby) {
        this.updatedby = updatedby;
    }

    public Date getUpdateddatetime() {
        return updateddatetime;
    }

    public void setUpdateddatetime(Date updateddatetime) {
        this.updateddatetime = updateddatetime;
    }

    public Set<CategorySubCategory> getCategorySubCategories() {
        return categorySubCategories;
    }

    public void setCategorySubCategories(Set<CategorySubCategory> categorySubCategories) {
        this.categorySubCategories = categorySubCategories;
    }
}





