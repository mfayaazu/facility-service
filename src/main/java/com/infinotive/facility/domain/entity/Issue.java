package com.infinotive.facility.domain.entity;

import com.infinotive.facility.enums.IssueCategory;
import com.infinotive.facility.enums.IssuePriority;
import com.infinotive.facility.enums.IssueStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a reported problem within a tenant's environment.
 * Tracks category, priority, current status and contextual details.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "issues")
public class Issue extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "tenant_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_issue_tenant")
    )
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "location_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_issue_location")
    )
    private Location location;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 64)
    private IssueCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 32)
    private IssuePriority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private IssueStatus status = IssueStatus.OPEN;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "reporter_email", length = 320)
    private String reporterEmail;

    @Column(name = "reporter_phone", length = 64)
    private String reporterPhone;

    @Getter
    @Column(name = "reference_number", unique = true, nullable = false, length = 50)
    private String referenceNumber;


    public Issue(
            Tenant tenant,
            Location location,
            IssueCategory category,
            IssuePriority priority,
            String title,
            String description,
            String reporterEmail,
            String reporterPhone
    ) {
        this.tenant = tenant;
        this.location = location;
        this.category = category;
        this.priority = priority;
        this.title = title;
        this.description = description;
        this.reporterEmail = reporterEmail;
        this.reporterPhone = reporterPhone;
        this.status = IssueStatus.OPEN;
    }

    public void changeStatus(IssueStatus newStatus) {
        this.status = newStatus;
    }

    public void changePriority(IssuePriority newPriority) {
        this.priority = newPriority;
    }

    public void assignReferenceNumber(String ref) {
        this.referenceNumber = ref;
    }

}
