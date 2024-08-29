package com.oie.cms.repositories.hiring;

import com.oie.cms.entities.hiring.InterviewCandidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterviewCandidateRepository extends JpaRepository<InterviewCandidate, Long> {
    Optional<InterviewCandidate> findByPhoneNumber(String phoneNumber);
}
