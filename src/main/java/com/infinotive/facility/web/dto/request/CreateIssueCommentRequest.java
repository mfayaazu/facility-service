package com.infinotive.facility.web.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Payload for creating a new comment on an issue.
 */
@Getter
@Setter
public class CreateIssueCommentRequest {
    private String text;
}
