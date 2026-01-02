package com.infinotive.facility.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Metadata about an issue attachment.
 */
@Getter
@AllArgsConstructor
public class IssueAttachmentResponse {

    private String id;
    private String originalFilename;
    private String contentType;
    private long sizeBytes;
}
