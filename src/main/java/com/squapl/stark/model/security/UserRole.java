package com.squapl.stark.model.security;

import com.squapl.stark.model.User;

import javax.persistence.*;
import java.math.BigInteger;


@Entity
@Table(name = "user_role")
public class UserRole {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private BigInteger id;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }


    public UserRole() {
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


}
