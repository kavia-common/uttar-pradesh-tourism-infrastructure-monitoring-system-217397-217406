package com.example.demo.repository.domain;

import com.example.demo.model.domain.NotificationQueue;
import com.example.demo.model.domain.NotificationQueue.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for NotificationQueue entity.
 */
public interface NotificationQueueRepository extends JpaRepository<NotificationQueue, Long> {

    /**
     * PUBLIC_INTERFACE
     * Find a limited batch of PENDING notifications that are scheduled to be sent (scheduledAt <= now OR null).
     * Uses PESSIMISTIC_WRITE to avoid double-processing across concurrent schedulers.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT n FROM NotificationQueue n " +
            "WHERE n.status = :status " +
            "AND (n.scheduledAt IS NULL OR n.scheduledAt <= :now) " +
            "ORDER BY n.createdAt ASC")
    List<NotificationQueue> findPendingBatch(Status status, LocalDateTime now);
}
