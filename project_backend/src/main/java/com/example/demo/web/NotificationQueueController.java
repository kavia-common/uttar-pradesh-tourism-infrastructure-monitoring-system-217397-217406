package com.example.demo.web;

import com.example.demo.model.domain.NotificationQueue;
import com.example.demo.service.NotificationQueueService;
import com.example.demo.service.NotificationQueueService.EnqueueRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Minimal API for notification queue: enqueue and list recent.
 * Secured by RBAC using NOTIFICATION_WRITE for enqueue and NOTIFICATION_READ for listing.
 */
@RestController
@RequestMapping("/api/notify")
@Tag(name = "Notifications", description = "Notification queue management (enqueue and list)")
public class NotificationQueueController {

    private final NotificationQueueService service;

    public NotificationQueueController(NotificationQueueService service) {
        this.service = service;
    }

    /**
     * PUBLIC_INTERFACE
     * Enqueue an outbound email.
     */
    @PostMapping("/enqueue")
    @PreAuthorize("hasAuthority('NOTIFICATION_WRITE') or hasRole('ADMIN')")
    @Operation(summary = "Enqueue email notification", description = "Adds a notification to the queue with optional scheduled time.")
    public ResponseEntity<NotificationQueue> enqueue(@Valid @RequestBody EnqueueRequest req) {
        return ResponseEntity.ok(service.enqueue(req));
    }

    /**
     * PUBLIC_INTERFACE
     * List recent queued items (default 50).
     */
    @GetMapping("/recent")
    @PreAuthorize("hasAuthority('NOTIFICATION_READ') or hasRole('ADMIN')")
    @Operation(summary = "List recent notifications", description = "Lists the most recent queued notifications for operational visibility.")
    public ResponseEntity<List<NotificationQueue>> recent(@RequestParam(name = "limit", required = false, defaultValue = "50") int limit) {
        return ResponseEntity.ok(service.listRecent(limit));
    }
}
