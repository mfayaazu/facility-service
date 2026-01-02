package com.infinotive.facility.web.dto.response;

import com.infinotive.facility.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

/**
 * API response representation of an issue comment.
 */
@Getter
@AllArgsConstructor
public class IssueCommentResponse {

    private String id;
    private String authorId;
    private String authorEmail;
    private UserRole authorRole;
    private String text;
    private OffsetDateTime createdAt;
}
