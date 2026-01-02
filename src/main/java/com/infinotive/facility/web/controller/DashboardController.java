package com.infinotive.facility.web.controller;

import com.infinotive.facility.domain.service.DashboardService;
import com.infinotive.facility.security.CurrentUser;
import com.infinotive.facility.security.JwtService;
import com.infinotive.facility.web.dto.response.DashboardSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes aggregated statistics for the dashboard.
 */
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public DashboardSummaryResponse getSummary() {
        JwtService.JwtPrincipal principal = CurrentUser.get();

        var summary = dashboardService.getSummaryForTenant(principal.tenantId());

        return new DashboardSummaryResponse(
                summary.totalIssues(),
                summary.byStatus(),
                summary.byPriority()
        );
    }
}
