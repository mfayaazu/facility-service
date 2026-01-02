package com.infinotive.facility.domain.repository;

import com.infinotive.facility.domain.entity.Issue;
import com.infinotive.facility.domain.entity.IssueAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueAttachmentRepository extends JpaRepository<IssueAttachment, String> {

    List<IssueAttachment> findAllByIssue(Issue issue);
}
