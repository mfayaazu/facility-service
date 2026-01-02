package com.infinotive.facility.domain.repository;

import com.infinotive.facility.domain.entity.IssueSequence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueSequenceRepository extends JpaRepository<IssueSequence, String> {
}
