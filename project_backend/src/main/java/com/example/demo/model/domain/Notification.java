package com.example.demo.model.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Simple notification to a user.
 */
@Entity
@Table(name = "notifications")
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=80)
    private String username; // recipient username

    @Column(nullable=false, length=500)
    private String message;

    private boolean readFlag = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Notification(){}

    // PUBLIC_INTERFACE
    public Long getId() { return id; }
    // PUBLIC_INTERFACE
    public void setId(Long id) { this.id = id; }
    // PUBLIC_INTERFACE
    public String getUsername() { return username; }
    // PUBLIC_INTERFACE
    public void setUsername(String username) { this.username = username; }
    // PUBLIC_INTERFACE
    public String getMessage() { return message; }
    // PUBLIC_INTERFACE
    public void setMessage(String message) { this.message = message; }
    // PUBLIC_INTERFACE
    public boolean isReadFlag() { return readFlag; }
    // PUBLIC_INTERFACE
    public void setReadFlag(boolean readFlag) { this.readFlag = readFlag; }
    // PUBLIC_INTERFACE
    public LocalDateTime getCreatedAt() { return createdAt; }
    // PUBLIC_INTERFACE
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
