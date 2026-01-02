package com.infinotive.facility.web.controller;

import com.infinotive.facility.domain.entity.IssueAttachment;
import com.infinotive.facility.domain.service.IssueAttachmentService;
import com.infinotive.facility.security.CurrentUser;
import com.infinotive.facility.security.JwtService;
import com.infinotive.facility.web.dto.response.IssueAttachmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * HTTP endpoints for managing file attachments on issues.
 */
@RestController
@RequestMapping("/api/issues/{referenceNumber}/attachments")
@RequiredArgsConstructor
public class IssueAttachmentController {

    private final IssueAttachmentService attachmentService;

    @GetMapping
    public List<IssueAttachmentResponse> list(@PathVariable String referenceNumber) {
        JwtService.JwtPrincipal principal = CurrentUser.get();

        List<IssueAttachment> attachments =
                attachmentService.listAttachments(principal.tenantId(), referenceNumber);

        return attachments.stream()
                .map(a -> new IssueAttachmentResponse(
                        a.getId(),
                        a.getOriginalFilename(),
                        a.getContentType(),
                        a.getSizeBytes()
                ))
                .toList();
    }

    @PostMapping
    public ResponseEntity<IssueAttachmentResponse> upload(
            @PathVariable String referenceNumber,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        JwtService.JwtPrincipal principal = CurrentUser.get();

        IssueAttachment saved =
                attachmentService.addAttachment(principal.tenantId(), referenceNumber, file);

        IssueAttachmentResponse response = new IssueAttachmentResponse(
                saved.getId(),
                saved.getOriginalFilename(),
                saved.getContentType(),
                saved.getSizeBytes()
        );

        return ResponseEntity.ok(response);
    }
}
