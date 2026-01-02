package com.infinotive.facility.web.controller;

import com.infinotive.facility.domain.entity.IssueComment;
import com.infinotive.facility.domain.service.IssueCommentService;
import com.infinotive.facility.security.CurrentUser;
import com.infinotive.facility.security.JwtService;
import com.infinotive.facility.web.dto.request.CreateIssueCommentRequest;
import com.infinotive.facility.web.dto.response.IssueCommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * HTTP endpoints for managing comments on issues.
 */
@RestController
@RequestMapping("/api/issues/{referenceNumber}/comments")
@RequiredArgsConstructor
public class IssueCommentController {

    private final IssueCommentService commentService;

    @GetMapping
    public List<IssueCommentResponse> list(@PathVariable String referenceNumber) {
        JwtService.JwtPrincipal principal = CurrentUser.get();

        List<IssueComment> comments =
                commentService.listComments(principal.tenantId(), referenceNumber);

        return comments.stream()
                .map(c -> new IssueCommentResponse(
                        c.getId(),
                        c.getAuthor().getId(),
                        c.getAuthor().getEmail(),
                        c.getAuthor().getRole(),
                        c.getText(),
                        c.getCreatedAt()
                ))
                .toList();
    }

    @PostMapping
    public ResponseEntity<IssueCommentResponse> add(
            @PathVariable String referenceNumber,
            @RequestBody CreateIssueCommentRequest req
    ) {
        JwtService.JwtPrincipal principal = CurrentUser.get();

        IssueComment created = commentService.addComment(
                principal.tenantId(),
                referenceNumber,
                principal.userId(),
                req.getText()
        );

        IssueCommentResponse response = new IssueCommentResponse(
                created.getId(),
                created.getAuthor().getId(),
                created.getAuthor().getEmail(),
                created.getAuthor().getRole(),
                created.getText(),
                created.getCreatedAt()
        );

        return ResponseEntity.ok(response);
    }
}
