package com.squapl.stark.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@DynamicUpdate
@Entity
@Data

@Table(name = "center_details")
public class Center extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "corporate_id")
    private Corporate corporate;


    private String name;
 
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private JsonNode details;

    private String isactive;


}
