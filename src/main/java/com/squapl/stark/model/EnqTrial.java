package com.squapl.stark.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "enq_trial")
public class EnqTrial extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "enq_id")
    private Enquiry enquiry;

    private Date trial_on;
    private String status;
    private String notes;

}



