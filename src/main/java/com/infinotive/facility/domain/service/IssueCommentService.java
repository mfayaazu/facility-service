package com.infinotive.facility.domain.service;

import com.infinotive.facility.domain.entity.Issue;
import com.infinotive.facility.domain.entity.IssueComment;
import com.infinotive.facility.domain.entity.User;
import com.infinotive.facility.domain.repository.IssueCommentRepository;
import com.infinotive.facility.domain.repository.IssueRepository;
import com.infinotive.facility.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueCommentService {

    private final IssueRepository issueRepository;
    private final IssueCommentRepository commentRepository;
    private final UserRepository userRepository;

    public List<IssueComment> listComments(String tenantId, String referenceNumber) {
        Issue issue = issueRepository
                .findByTenant_IdAndReferenceNumber(tenantId, referenceNumber)
                .orElseThrow(() -> new IllegalArgumentException("Issue not found"));

        return commentRepository.findAllByIssueOrderByCreatedAtAsc(issue);
    }

    public IssueComment addComment(String tenantId, String referenceNumber, String userId, String text) {
        Issue issue = issueRepository
                .findByTenant_IdAndReferenceNumber(tenantId, referenceNumber)
                .orElseThrow(() -> new IllegalArgumentException("Issue not found"));

        if (!issue.getTenant().getId().equals(tenantId)) {
            throw new IllegalArgumentException("Issue does not belong to tenant");
        }

        User author = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!author.getTenant().getId().equals(tenantId)) {
            throw new IllegalArgumentException("User does not belong to tenant");
        }

        IssueComment comment = new IssueComment(issue, author, text);
        return commentRepository.save(comment);
    }
}
