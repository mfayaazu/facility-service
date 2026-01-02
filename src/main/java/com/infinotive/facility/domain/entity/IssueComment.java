package com.infinotive.facility.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A comment added to an issue by an authenticated user.
 * Used for internal conversation and status clarification.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "issue_comments")
public class IssueComment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "issue_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_comment_issue")
    )
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "author_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_comment_user")
    )
    private User author;

    @Column(name = "text", nullable = false, length = 4000)
    private String text;

    public IssueComment(Issue issue, User author, String text) {
        this.issue = issue;
        this.author = author;
        this.text = text;
    }
}
