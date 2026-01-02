package com.infinotive.facility.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Metadata of a file attached to an issue.
 * The file itself is stored on disk (or external storage).
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "issue_attachments")
public class IssueAttachment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "issue_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_attachment_issue")
    )
    private Issue issue;

    @Column(name = "original_filename", nullable = false, length = 255)
    private String originalFilename;

    @Column(name = "stored_filename", nullable = false, length = 255)
    private String storedFilename;

    @Column(name = "content_type", length = 255)
    private String contentType;

    @Column(name = "size_bytes", nullable = false)
    private long sizeBytes;

    @Column(name = "relative_path", nullable = false, length = 500)
    private String relativePath;

    public IssueAttachment(
            Issue issue,
            String originalFilename,
            String storedFilename,
            String contentType,
            long sizeBytes,
            String relativePath
    ) {
        this.issue = issue;
        this.originalFilename = originalFilename;
        this.storedFilename = storedFilename;
        this.contentType = contentType;
        this.sizeBytes = sizeBytes;
        this.relativePath = relativePath;
    }
}
