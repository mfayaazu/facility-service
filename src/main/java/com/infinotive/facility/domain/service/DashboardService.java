package com.infinotive.facility.domain.service;


import com.infinotive.facility.domain.repository.IssueRepository;
import com.infinotive.facility.enums.IssuePriority;
import com.infinotive.facility.enums.IssueStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

/**
 * Provides aggregated statistics for the dashboard view.
 */
@Service
@RequiredArgsConstructor
public class DashboardService {

    private final IssueRepository issueRepository;

    public DashboardSummary getSummaryForTenant(String tenantId) {
        long total = issueRepository.countByTenant_Id(tenantId);

        Map<IssueStatus, Long> byStatus = new EnumMap<>(IssueStatus.class);
        for (IssueStatus status : IssueStatus.values()) {
            long count = issueRepository.countByTenant_IdAndStatus(tenantId, status);
            byStatus.put(status, count);
        }

        Map<IssuePriority, Long> byPriority = new EnumMap<>(IssuePriority.class);
        for (IssuePriority priority : IssuePriority.values()) {
            long count = issueRepository.countByTenant_IdAndPriority(tenantId, priority);
            byPriority.put(priority, count);
        }

        return new DashboardSummary(total, byStatus, byPriority);
    }

    public record DashboardSummary(
            long totalIssues,
            Map<IssueStatus, Long> byStatus,
            Map<IssuePriority, Long> byPriority
    ) {}
}
