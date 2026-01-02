package com.infinotive.facility.domain.repository;

import com.infinotive.facility.domain.entity.Issue;
import com.infinotive.facility.domain.entity.IssueComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueCommentRepository extends JpaRepository<IssueComment, String> {

    List<IssueComment> findAllByIssueOrderByCreatedAtAsc(Issue issue);
}
