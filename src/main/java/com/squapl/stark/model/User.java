package com.squapl.stark.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.squapl.stark.model.security.UserRole;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@DynamicUpdate
@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column
    private String username;
    @Column
    @JsonIgnore
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "center_id")
    private Center center;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "corporate_id")
    private Corporate corporate;

    @Transient
    private String role;

    private String firstname;
    private String mobilenumber;
    private String profileimgurl;
    private String social_id;
    private String status;
    private String email;
    private String verified;
    private String gender;
    private String dob;
    private String signup_mode;
    private long createdby;
    private Date createddatetime;
    private long updatedby;
    private Date updateddatetime;


    //  The user in mappedBy is telling Hibernate where to find the configuration for the JoinColumn.
    // Go look over on the bean property named 'user' on the thing I have a collection of to find the configuration.
    // inside UserRole <entity> we have the user object, which maps as FK
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }


}