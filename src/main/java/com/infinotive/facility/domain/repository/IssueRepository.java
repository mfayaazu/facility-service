package com.infinotive.facility.domain.repository;

import com.infinotive.facility.domain.entity.Issue;
import com.infinotive.facility.enums.IssuePriority;
import com.infinotive.facility.enums.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue, String> {

    List<Issue> findAllByTenant_Id(String tenantId);

    long countByTenant_Id(String tenantId);

    long countByTenant_IdAndStatus(String tenantId, IssueStatus status);

    long countByTenant_IdAndPriority(String tenantId, IssuePriority priority);

    Optional<Issue> findByTenant_IdAndReferenceNumber(String tenantId, String referenceNumber);
}
