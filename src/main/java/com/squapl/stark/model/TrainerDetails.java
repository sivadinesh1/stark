package com.squapl.stark.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@DynamicUpdate
@Entity
@Data
@Table(name = "trainer_details")
public class TrainerDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User trainuser;

    private String level;
    private int trainerfee;

    private long createdby;
    private Date createddatetime;
    private long updatedby;
    private Date updateddatetime;


}



