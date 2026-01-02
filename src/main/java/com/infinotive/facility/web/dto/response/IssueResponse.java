package com.infinotive.facility.web.dto.response;

import com.infinotive.facility.enums.IssueCategory;
import com.infinotive.facility.enums.IssuePriority;
import com.infinotive.facility.enums.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * API response representation of an issue.
 */
@Getter
@AllArgsConstructor
public class IssueResponse {
    private String referenceNumber;
    private String locationId;
    private String locationName;
    private IssueCategory category;
    private IssuePriority priority;
    private IssueStatus status;
    private String title;
    private String description;
    private String reporterEmail;
    private String reporterPhone;
}
