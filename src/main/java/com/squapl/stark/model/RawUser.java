package com.squapl.stark.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "raw_user")
public class RawUser {
    @Id
    private long id;
    private String source_centerid;
    private long centerid;
    private String firstname;
    private String mobilenumber;
    private String servicevariation;
    private String startdate;
    private String enddate;
    private String isactive;
}
