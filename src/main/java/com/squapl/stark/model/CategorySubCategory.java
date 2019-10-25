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


//
//*****
//
//        package com.squapl.stark.model.security;
//
//        import com.squapl.stark.model.User;
//
//        import javax.persistence.*;
//        import java.math.BigInteger;


//@Entity
//@Table(name = "user_role")
//public class UserRole {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private BigInteger id;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_id")
//    private User user;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "role_id")
//    private Role role;
//
//    public UserRole(User user, Role role) {
//        this.user = user;
//        this.role = role;
//    }
//
//
//    public UserRole() {
//    }
//
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }


//}

