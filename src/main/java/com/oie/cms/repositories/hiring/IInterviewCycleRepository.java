package com.oie.cms.repositories.hiring;

import com.oie.cms.entities.hiring.InterviewCycle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInterviewCycleRepository extends JpaRepository<InterviewCycle, Long> {
}
