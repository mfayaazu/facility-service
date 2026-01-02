package com.infinotive.facility.web.controller;

import com.infinotive.facility.domain.entity.Issue;
import com.infinotive.facility.domain.service.IssueService;
import com.infinotive.facility.domain.repository.TenantRepository;
import com.infinotive.facility.security.CurrentUser;
import com.infinotive.facility.web.dto.request.CreateIssueRequest;
import com.infinotive.facility.web.dto.response.IssueResponse;
import com.infinotive.facility.web.dto.request.UpdateIssueStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HTTP endpoints for issue management.
 */
@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;
    private final TenantRepository tenantRepository;

    @GetMapping
    public List<IssueResponse> list() {
        var principal = CurrentUser.get();

        List<Issue> issues = issueService.findAllByTenant(principal.tenantId());

        return issues.stream()
                .map(i -> new IssueResponse(
                        i.getReferenceNumber(),
                        i.getLocation().getId(),
                        i.getLocation().getName(),
                        i.getCategory(),
                        i.getPriority(),
                        i.getStatus(),
                        i.getTitle(),
                        i.getDescription(),
                        i.getReporterEmail(),
                        i.getReporterPhone()
                ))
                .toList();
    }

    @PostMapping
    public ResponseEntity<IssueResponse> create(@RequestBody CreateIssueRequest req) {
        var principal = CurrentUser.get();

        var tenant = tenantRepository.findById(principal.tenantId())
                .orElseThrow(() -> new IllegalStateException("Tenant not found"));

        Issue created = issueService.createIssue(
                tenant,
                req.getLocationCode(),
                req.getCategory(),
                req.getPriority(),
                req.getTitle(),
                req.getDescription(),
                req.getReporterEmail(),
                req.getReporterPhone()
        );

        return ResponseEntity.ok(
                new IssueResponse(
                        created.getReferenceNumber(),
                        created.getLocation().getId(),
                        created.getLocation().getName(),
                        created.getCategory(),
                        created.getPriority(),
                        created.getStatus(),
                        created.getTitle(),
                        created.getDescription(),
                        created.getReporterEmail(),
                        created.getReporterPhone()
                )
        );
    }

    @PatchMapping("/{referenceNumber}")
    public ResponseEntity<IssueResponse> updateStatus(
            @PathVariable String referenceNumber,
            @RequestBody UpdateIssueStatusRequest req
    ) {
        var principal = CurrentUser.get();

        Issue updated = issueService.updateStatusAndPriorityByRef(
                principal.tenantId(),
                referenceNumber,
                req.getStatus(),
                req.getPriority()
        );

        return ResponseEntity.ok(
                new IssueResponse(
                        updated.getReferenceNumber(),
                        updated.getLocation().getId(),
                        updated.getLocation().getName(),
                        updated.getCategory(),
                        updated.getPriority(),
                        updated.getStatus(),
                        updated.getTitle(),
                        updated.getDescription(),
                        updated.getReporterEmail(),
                        updated.getReporterPhone()
                )
        );
    }
}
