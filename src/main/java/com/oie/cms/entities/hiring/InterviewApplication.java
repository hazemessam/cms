package com.oie.cms.entities.hiring;

import com.oie.cms.entities.BaseEntity;
import com.oie.cms.entities.department.Department;
import com.oie.cms.entities.employee.Employee;
import com.oie.cms.enums.Position;
import com.oie.cms.enums.ReferralType;
import com.oie.cms.utils.converters.PositionConverter;
import com.oie.cms.utils.converters.ReferralTypeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "interview_applications")
@Getter
@Setter
public class InterviewApplication extends BaseEntity {
    @Column(name = "cv_url")
    private String cvUrl;

    @Convert(converter = PositionConverter.class)
    private Position position;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    private InterviewCandidate candidate;

    @Column(name = "referral_type")
    @Convert(converter = ReferralTypeConverter.class)
    private ReferralType referralType;

    @ManyToOne
    @JoinColumn(name = "referral_employee_id", referencedColumnName = "id")
    private Employee referralEmployee;
}
