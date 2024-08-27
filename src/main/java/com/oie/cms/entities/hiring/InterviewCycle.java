package com.oie.cms.entities.hiring;

import com.oie.cms.entities.BaseEntity;
import com.oie.cms.enums.InterviewCycleStatus;
import com.oie.cms.utils.converters.InterviewCycleStatusConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "interview_cycles")
@Getter
@Setter
public class InterviewCycle extends BaseEntity {
    @Convert(converter = InterviewCycleStatusConverter.class)
    private InterviewCycleStatus status;

    private Float rating;

    @OneToOne
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    private InterviewApplication application;

    @OneToMany(mappedBy = "cycle", fetch = FetchType.LAZY)
    private List<Interview> interviews;
}
