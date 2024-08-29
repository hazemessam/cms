package com.oie.cms.repositories.hiring;

import com.oie.cms.entities.hiring.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
}
