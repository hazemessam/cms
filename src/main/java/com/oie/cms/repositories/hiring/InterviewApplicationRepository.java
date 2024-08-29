package com.oie.cms.repositories.hiring;

import com.oie.cms.entities.hiring.InterviewApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewApplicationRepository extends JpaRepository<InterviewApplication, Long> {
}
