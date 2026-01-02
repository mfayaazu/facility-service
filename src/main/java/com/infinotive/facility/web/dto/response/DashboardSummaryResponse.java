package com.infinotive.facility.web.dto.response;


import com.infinotive.facility.enums.IssuePriority;
import com.infinotive.facility.enums.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * Summary view of issues for the current tenant,
 * used to power the dashboard page.
 */
@Getter
@AllArgsConstructor
public class DashboardSummaryResponse {

    private long totalIssues;
    private Map<IssueStatus, Long> byStatus;
    private Map<IssuePriority, Long> byPriority;
}
