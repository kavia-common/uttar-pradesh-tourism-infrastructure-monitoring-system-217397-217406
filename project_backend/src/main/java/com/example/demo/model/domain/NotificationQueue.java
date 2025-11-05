package com.example.demo.model.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Queue table for outbound notifications (primarily email, can be extended).
 * Fields capture lifecycle: status, retry_count, scheduling and timestamps.
 */
@Entity
@Table(name = "notification_queue", indexes = {
        @Index(name = "idx_notification_queue_status_sched", columnList = "status, scheduledAt"),
        @Index(name = "idx_notification_queue_created", columnList = "createdAt")
})
public class NotificationQueue {

    public enum Status {
        PENDING, SENT, FAILED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // destination email; can be extended later for SMS/push
    @Column(length = 200)
    private String toEmail;

    @Column(length = 200)
    private String subject;

    @Column(length = 4000)
    private String body;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.PENDING;

    @Column(nullable = false)
    private int retryCount = 0;

    private LocalDateTime scheduledAt;

    private LocalDateTime sentAt;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public NotificationQueue() {}

    // PUBLIC_INTERFACE
    public Long getId() { return id; }
    // PUBLIC_INTERFACE
    public void setId(Long id) { this.id = id; }

    // PUBLIC_INTERFACE
    public String getToEmail() { return toEmail; }
    // PUBLIC_INTERFACE
    public void setToEmail(String toEmail) { this.toEmail = toEmail; }

    // PUBLIC_INTERFACE
    public String getSubject() { return subject; }
    // PUBLIC_INTERFACE
    public void setSubject(String subject) { this.subject = subject; }

    // PUBLIC_INTERFACE
    public String getBody() { return body; }
    // PUBLIC_INTERFACE
    public void setBody(String body) { this.body = body; }

    // PUBLIC_INTERFACE
    public Status getStatus() { return status; }
    // PUBLIC_INTERFACE
    public void setStatus(Status status) { this.status = status; }

    // PUBLIC_INTERFACE
    public int getRetryCount() { return retryCount; }
    // PUBLIC_INTERFACE
    public void setRetryCount(int retryCount) { this.retryCount = retryCount; }

    // PUBLIC_INTERFACE
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    // PUBLIC_INTERFACE
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }

    // PUBLIC_INTERFACE
    public LocalDateTime getSentAt() { return sentAt; }
    // PUBLIC_INTERFACE
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }

    // PUBLIC_INTERFACE
    public LocalDateTime getCreatedAt() { return createdAt; }
    // PUBLIC_INTERFACE
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
