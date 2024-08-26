package com.oie.cms.entities.hiring;

import com.oie.cms.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "interview_candidates")
@Getter
@Setter
public class InterviewCandidate extends BaseEntity {
    private String name;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "candidate")
    private List<InterviewApplication> applications;
}
