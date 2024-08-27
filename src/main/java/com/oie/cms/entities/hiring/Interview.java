package com.oie.cms.entities.hiring;

import com.oie.cms.entities.BaseEntity;
import com.oie.cms.entities.employee.Employee;
import com.oie.cms.enums.InterviewType;
import com.oie.cms.utils.converters.InterviewTypeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "interviews")
@Getter
@Setter
public class Interview extends BaseEntity {
    private Instant appointment;

    private Integer rating;

    @Convert(converter = InterviewTypeConverter.class)
    private InterviewType type;

    @ManyToOne
    @JoinColumn(name = "interview_cycle_id", referencedColumnName = "id")
    private InterviewCycle cycle;

    @ManyToOne
    @JoinColumn(name = "interviewer_id", referencedColumnName = "id")
    private Employee interviewer;
}
