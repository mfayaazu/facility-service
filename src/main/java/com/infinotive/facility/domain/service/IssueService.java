package com.infinotive.facility.domain.service;

import com.infinotive.facility.domain.entity.*;
import com.infinotive.facility.domain.exception.ResourceNotFoundException;
import com.infinotive.facility.domain.repository.IssueRepository;
import com.infinotive.facility.domain.repository.IssueSequenceRepository;
import com.infinotive.facility.domain.repository.LocationRepository;
import com.infinotive.facility.enums.IssueCategory;
import com.infinotive.facility.enums.IssuePriority;
import com.infinotive.facility.enums.IssueStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final LocationRepository locationRepository;
    private final IssueSequenceRepository sequenceRepository;
    private final EmailNotificationService emailNotificationService;


    public List<Issue> findAllByTenant(String tenantId) {
        return issueRepository.findAllByTenant_Id(tenantId);
    }

    public Issue createIssue(
            Tenant tenant,
            String locationCode,
            IssueCategory category,
            IssuePriority priority,
            String title,
            String description,
            String reporterEmail,
            String reporterPhone
    ) {
        var location = locationRepository.findByTenant_IdAndCode(tenant.getId(), locationCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Location not found for code: " + locationCode
                ));

        if (!location.getTenant().getId().equals(tenant.getId())) {
            throw new IllegalArgumentException("Location does not belong to tenant");
        }

        Issue issue = new Issue(
                tenant,
                location,
                category,
                priority,
                title,
                description,
                reporterEmail,
                reporterPhone
        );

        String ref = nextReferenceNumberForTenant(tenant.getId());

        issue.assignReferenceNumber(ref);
        Issue saved = issueRepository.save(issue);
        emailNotificationService.sendIssueStatusChanged(saved);

        return saved;
    }

    public Issue updateStatusAndPriorityByRef(
            String tenantId,
            String referenceNumber,
            IssueStatus status,
            IssuePriority priority
    ) {
        Issue issue = issueRepository.findByTenant_IdAndReferenceNumber(tenantId, referenceNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Issue not found for reference: " + referenceNumber));

        if (status != null) {
            issue.changeStatus(status);
        }
        if (priority != null) {
            issue.changePriority(priority);
        }

        return issueRepository.save(issue);
    }

    @Transactional
    protected String nextReferenceNumberForTenant(String tenantId) {

        IssueSequence seq = sequenceRepository.findById(tenantId)
                .orElseGet(() -> sequenceRepository.save(new IssueSequence(tenantId)));

        Long next = seq.next();
        sequenceRepository.save(seq);

        return String.format("INC%06d", next);
    }
}
