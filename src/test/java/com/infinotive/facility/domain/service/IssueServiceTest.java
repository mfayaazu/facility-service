package com.infinotive.facility.domain.service;

import com.infinotive.facility.domain.entity.*;
import com.infinotive.facility.domain.exception.ResourceNotFoundException;
import com.infinotive.facility.domain.repository.IssueRepository;
import com.infinotive.facility.domain.repository.IssueSequenceRepository;
import com.infinotive.facility.domain.repository.LocationRepository;
import com.infinotive.facility.domain.repository.TenantRepository;
import com.infinotive.facility.enums.IssueCategory;
import com.infinotive.facility.enums.IssuePriority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IssueServiceTest {

    private IssueRepository issueRepository;
    private LocationRepository locationRepository;
    private EmailNotificationService emailNotificationService;
    private IssueSequenceRepository issueSequenceRepository;
    private TenantRepository tenantRepository;

    private IssueService issueService;

    @BeforeEach
    void setUp() {
        issueRepository = mock(IssueRepository.class);
        locationRepository = mock(LocationRepository.class);
        tenantRepository = mock(TenantRepository.class);
        issueSequenceRepository = mock(IssueSequenceRepository.class);

        issueService = new IssueService(
                issueRepository,
                locationRepository,
                issueSequenceRepository,
                emailNotificationService  // EmailNotificationService - you can mock and pass later
        );
    }

    @Test
    void createIssue_throwsResourceNotFound_whenLocationCodeIsInvalid() {
        Tenant tenant = new Tenant("demo", "Demo Tenant");
        when(tenantRepository.findById("tenant-1"))
                .thenReturn(Optional.of(tenant));

        when(locationRepository.findByTenant_IdAndCode("tenant-1", "ROOM-999"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                issueService.createIssue(
                        tenant,
                        "ROOM-999",
                        IssueCategory.ELECTRICAL,
                        IssuePriority.HIGH,
                        "Test",
                        "Desc",
                        "user@example.com",
                        "+4673"
                )
        )
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Location not found for code: ROOM-999");
    }
}
