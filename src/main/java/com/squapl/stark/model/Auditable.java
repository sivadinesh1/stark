package com.squapl.stark.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;


@Getter(AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable<U> {
    @CreatedBy
    @Column(name = "created_by")
    private U createdBy;

    // @CreatedDate
    @CreationTimestamp
    @Column(name = "created_datetime")
    private Date createdDateTime;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private U lastModifiedBy;

    // @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "last_modified_datetime")
    private Date lastModifiedDateTime;
}