package com.example.demo.service;

import com.example.demo.model.domain.NotificationQueue;
import com.example.demo.model.domain.NotificationQueue.Status;
import com.example.demo.repository.domain.NotificationQueueRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service that provides enqueue and background processing for outbound notifications.
 * If mail environment variables are not configured, messages are logged as sent (SENT) to avoid blocking flows.
 */
@Service
public class NotificationQueueService {

    private static final Logger log = LoggerFactory.getLogger(NotificationQueueService.class);

    private final NotificationQueueRepository repo;
    private final JavaMailSender mailSender;
    private final boolean mailEnabled;
    private final int maxRetries;
    private final int batchSize;

    public NotificationQueueService(NotificationQueueRepository repo,
                                    JavaMailSender mailSender,
                                    // When spring.mail.host (or MAIL_ENABLED) is non-empty, we consider mail enabled
                                    @Value("${MAIL_ENABLED:${spring.mail.host:}}") String mailHostOrEnabledFlag,
                                    @Value("${NOTIFY_MAX_RETRIES:3}") int maxRetries,
                                    @Value("${NOTIFY_BATCH_SIZE:25}") int batchSize) {
        this.repo = repo;
        this.mailSender = mailSender;
        this.mailEnabled = mailHostOrEnabledFlag != null && !mailHostOrEnabledFlag.isBlank();
        this.maxRetries = Math.max(0, maxRetries);
        this.batchSize = Math.max(1, batchSize);
    }

    /**
     * PUBLIC_INTERFACE
     * Enqueue an email notification for delivery. Requires NOTIFICATION_WRITE or ADMIN.
     */
    @PreAuthorize("hasAuthority('NOTIFICATION_WRITE') or hasRole('ADMIN')")
    public NotificationQueue enqueue(EnqueueRequest req) {
        NotificationQueue n = new NotificationQueue();
        n.setToEmail(req.toEmail);
        n.setSubject(req.subject);
        n.setBody(req.body);
        n.setScheduledAt(req.scheduledAt);
        n.setStatus(Status.PENDING);
        n.setRetryCount(0);
        return repo.save(n);
    }

    /**
     * PUBLIC_INTERFACE
     * List last N queued items (simple helper for admins/ops).
     */
    @PreAuthorize("hasAuthority('NOTIFICATION_READ') or hasRole('ADMIN')")
    public List<NotificationQueue> listRecent(int limit) {
        var all = repo.findAll();
        // naive in-memory trim; acceptable for stub; can switch to pageable later
        if (all.size() <= limit) return all;
        return all.subList(Math.max(0, all.size() - limit), all.size());
    }

    /**
     * Scheduled processor (fixed delay) to attempt sending queued notifications.
     * Uses pessimistic locking query in repository to avoid duplicates across instances.
     */
    @Scheduled(fixedDelayString = "${NOTIFY_SCHEDULER_DELAY_MS:60000}")
    @Transactional
    public void processQueue() {
        List<NotificationQueue> pending = repo.findPendingBatch(Status.PENDING, LocalDateTime.now());
        int processed = 0;
        for (NotificationQueue item : pending) {
            if (processed >= batchSize) break;
            try {
                attemptSend(item);
            } catch (Exception ex) {
                log.warn("Notification send attempt failed for id {}: {}", item.getId(), ex.getMessage());
                handleFailure(item, ex);
            }
            processed++;
        }
    }

    private void attemptSend(NotificationQueue item) {
        if (!mailEnabled) {
            // Log-only mode; mark as SENT to keep queue clean.
            log.info("MAIL disabled. Simulating send to {} subject='{}'", item.getToEmail(), truncate(item.getSubject(), 100));
            item.setStatus(Status.SENT);
            item.setSentAt(LocalDateTime.now());
            repo.save(item);
            return;
        }

        if (item.getToEmail() == null || item.getToEmail().isBlank()) {
            throw new IllegalArgumentException("toEmail is required for email delivery");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(item.getToEmail());
        message.setSubject(item.getSubject() == null ? "" : item.getSubject());
        message.setText(item.getBody() == null ? "" : item.getBody());

        try {
            mailSender.send(message);
            item.setStatus(Status.SENT);
            item.setSentAt(LocalDateTime.now());
            repo.save(item);
        } catch (MailException ex) {
            handleFailure(item, ex);
        }
    }

    private void handleFailure(NotificationQueue item, Exception ex) {
        int retries = item.getRetryCount() + 1;
        item.setRetryCount(retries);
        if (retries > maxRetries) {
            item.setStatus(Status.FAILED);
        } else {
            item.setStatus(Status.PENDING);
            // simple backoff: schedule next attempt a minute later per retry
            item.setScheduledAt(LocalDateTime.now().plusMinutes(retries));
        }
        repo.save(item);
    }

    private String truncate(String s, int len) {
        if (s == null) return null;
        return s.length() > len ? s.substring(0, len - 1) + "…" : s;
    }

    /**
     * Request DTO for enqueue endpoint.
     */
    public static class EnqueueRequest {
        @Schema(description = "Email address of recipient", example = "user@example.com")
        @NotBlank @Email public String toEmail;

        @Schema(description = "Email subject", example = "Milestone Update")
        @NotBlank public String subject;

        @Schema(description = "Email body", example = "Your milestone has been updated.")
        @NotBlank public String body;

        @Schema(description = "Optional schedule time; if null, send asap")
        public LocalDateTime scheduledAt;
    }
}
