package com.infinotive.facility.domain.service;

import com.infinotive.facility.domain.entity.Issue;
import com.infinotive.facility.domain.entity.IssueAttachment;
import com.infinotive.facility.domain.repository.IssueAttachmentRepository;
import com.infinotive.facility.domain.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueAttachmentService {

    private final IssueRepository issueRepository;
    private final IssueAttachmentRepository attachmentRepository;
    private final FileStorageService fileStorageService;

    public List<IssueAttachment> listAttachments(String tenantId, String referenceNumber) {
        Issue issue = issueRepository
                .findByTenant_IdAndReferenceNumber(tenantId, referenceNumber)
                .orElseThrow(() -> new IllegalArgumentException("Issue not found"));

        return attachmentRepository.findAllByIssue(issue);
    }

    public IssueAttachment addAttachment(String tenantId, String referenceNumber, MultipartFile file) throws IOException {
        Issue issue = issueRepository
                .findByTenant_IdAndReferenceNumber(tenantId, referenceNumber)
                .orElseThrow(() -> new IllegalArgumentException("Issue not found"));

        if (!issue.getTenant().getId().equals(tenantId)) {
            throw new IllegalArgumentException("Issue does not belong to tenant");
        }

        var stored = fileStorageService.store(
                tenantId,
                issue.getReferenceNumber(),
                file
        );

        IssueAttachment attachment = new IssueAttachment(
                issue,
                stored.originalFilename(),
                stored.storedFilename(),
                stored.contentType(),
                stored.sizeBytes(),
                stored.relativePath()
        );

        return attachmentRepository.save(attachment);
    }
}
