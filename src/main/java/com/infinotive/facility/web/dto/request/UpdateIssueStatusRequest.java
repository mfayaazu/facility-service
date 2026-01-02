package com.infinotive.facility.web.dto.request;

import com.infinotive.facility.enums.IssuePriority;
import com.infinotive.facility.enums.IssueStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * Payload for updating status/priority of an issue.
 */
@Getter
@Setter
public class UpdateIssueStatusRequest {

    private IssueStatus status;
    private IssuePriority priority;
}
