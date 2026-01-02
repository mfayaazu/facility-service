package com.infinotive.facility.domain.service;


import com.infinotive.facility.domain.repository.IssueRepository;
import com.infinotive.facility.enums.IssuePriority;
import com.infinotive.facility.enums.IssueStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DashboardServiceTest {

    private IssueRepository issueRepository;
    private DashboardService dashboardService;

    @BeforeEach
    void setUp() {
        issueRepository = mock(IssueRepository.class);
        dashboardService = new DashboardService(issueRepository);
    }

    @Test
    void getSummaryForTenant_returnsAggregatedCounts() {
        String tenantId = "tenant-1";

        when(issueRepository.countByTenant_Id(tenantId)).thenReturn(10L);
        when(issueRepository.countByTenant_IdAndStatus(tenantId, IssueStatus.OPEN)).thenReturn(4L);
        when(issueRepository.countByTenant_IdAndStatus(tenantId, IssueStatus.IN_PROGRESS)).thenReturn(3L);
        when(issueRepository.countByTenant_IdAndStatus(tenantId, IssueStatus.RESOLVED)).thenReturn(2L);
        when(issueRepository.countByTenant_IdAndStatus(tenantId, IssueStatus.CLOSED)).thenReturn(1L);

        when(issueRepository.countByTenant_IdAndPriority(tenantId, IssuePriority.LOW)).thenReturn(1L);
        when(issueRepository.countByTenant_IdAndPriority(tenantId, IssuePriority.MEDIUM)).thenReturn(3L);
        when(issueRepository.countByTenant_IdAndPriority(tenantId, IssuePriority.HIGH)).thenReturn(4L);
        when(issueRepository.countByTenant_IdAndPriority(tenantId, IssuePriority.CRITICAL)).thenReturn(2L);

        var summary = dashboardService.getSummaryForTenant(tenantId);

        assertThat(summary.totalIssues()).isEqualTo(10L);
        assertThat(summary.byStatus().get(IssueStatus.OPEN)).isEqualTo(4L);
        assertThat(summary.byPriority().get(IssuePriority.CRITICAL)).isEqualTo(2L);
    }
}
