package com.infinotive.facility.web.dto.request;

import com.infinotive.facility.enums.IssueCategory;
import com.infinotive.facility.enums.IssuePriority;
import lombok.Getter;
import lombok.Setter;

/**
 * Payload for creating a new issue.
 */
@Getter
@Setter
public class CreateIssueRequest {
    private String locationCode;
    private IssueCategory category;
    private IssuePriority priority;
    private String title;
    private String description;
    private String reporterEmail;
    private String reporterPhone;
}
