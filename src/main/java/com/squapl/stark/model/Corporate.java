package com.squapl.stark.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;

@DynamicUpdate
@Entity
@Data
@TypeDef(
        name = "jsonb",
        typeClass = JsonBinaryType.class
)
@Table(name = "corporate_details")
public class Corporate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String name;


    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private JsonNode details;

    private String isactive;

    private long createdby;
    private Date createddatetime;
    private long updatedby;
    private Date updateddatetime;

}
