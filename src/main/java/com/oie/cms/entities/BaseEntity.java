package com.oie.cms.entities;

import com.oie.cms.entities.employee.Employee;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SoftDelete
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private Instant updatedAt;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id", updatable = false)
    private Employee createdBy;

    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "updated_by", referencedColumnName = "id", insertable = false)
    private Employee updatedBy;
}
