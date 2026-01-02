package com.infinotive.facility.domain.service;

import com.infinotive.facility.config.NotificationEmailProperties;
import com.infinotive.facility.domain.entity.Issue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Sends email notifications for issue events.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailNotificationService {

    private final JavaMailSender mailSender;
    private final NotificationEmailProperties props;

    public void sendIssueCreated(Issue issue) {
        if (!props.isEnabled()) {
            log.info("Email notifications disabled. Skipping issue-created email for {}.", issue.getReferenceNumber());
            return;
        }

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(props.getFromAddress());
        msg.setTo(props.getToAdminAddress());
        msg.setSubject("New issue " + issue.getReferenceNumber() + " created");
        msg.setText(buildIssueCreatedBody(issue));

        mailSender.send(msg);
    }

    public void sendIssueStatusChanged(Issue issue) {
        if (!props.isEnabled()) {
            log.info("Email notifications disabled. Skipping issue-status email for {}.", issue.getReferenceNumber());
            return;
        }

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(props.getFromAddress());
        msg.setTo(props.getToAdminAddress());
        msg.setSubject("Issue " + issue.getReferenceNumber() + " status changed");
        msg.setText(buildIssueStatusBody(issue));

        mailSender.send(msg);
    }

    private String buildIssueCreatedBody(Issue issue) {
        return """
                A new issue has been created.

                Reference: %s
                Title: %s
                Location: %s
                Category: %s
                Priority: %s
                Status: %s

                Description:
                %s
                """.formatted(
                issue.getReferenceNumber(),
                issue.getTitle(),
                issue.getLocation().getName(),
                issue.getCategory(),
                issue.getPriority(),
                issue.getStatus(),
                issue.getDescription()
        );
    }

    private String buildIssueStatusBody(Issue issue) {
        return """
                Issue status has been updated.

                Reference: %s
                Title: %s
                Location: %s
                Priority: %s
                Status: %s
                """.formatted(
                issue.getReferenceNumber(),
                issue.getTitle(),
                issue.getLocation().getName(),
                issue.getPriority(),
                issue.getStatus()
        );
    }
}
