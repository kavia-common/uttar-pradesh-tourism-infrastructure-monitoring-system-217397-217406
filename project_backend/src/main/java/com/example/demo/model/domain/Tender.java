package com.example.demo.model.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Tender details for a project.
 */
@Entity
@Table(name = "tenders")
public class Tender {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="project_id")
    private Project project;

    @Column(nullable=false, unique=true, length=150)
    private String number;

    private LocalDate publishDate;
    private LocalDate bidOpenDate;

    @Column(precision = 15, scale = 2)
    private BigDecimal estimatedCost;

    @Column(length=50)
    private String status; // PUBLISHED, AWARDED, CANCELLED

    public Tender() {}

    // PUBLIC_INTERFACE
    public Long getId() { return id; }
    // PUBLIC_INTERFACE
    public void setId(Long id) { this.id = id; }
    // PUBLIC_INTERFACE
    public Project getProject() { return project; }
    // PUBLIC_INTERFACE
    public void setProject(Project project) { this.project = project; }
    // PUBLIC_INTERFACE
    public String getNumber() { return number; }
    // PUBLIC_INTERFACE
    public void setNumber(String number) { this.number = number; }
    // PUBLIC_INTERFACE
    public LocalDate getPublishDate() { return publishDate; }
    // PUBLIC_INTERFACE
    public void setPublishDate(LocalDate publishDate) { this.publishDate = publishDate; }
    // PUBLIC_INTERFACE
    public LocalDate getBidOpenDate() { return bidOpenDate; }
    // PUBLIC_INTERFACE
    public void setBidOpenDate(LocalDate bidOpenDate) { this.bidOpenDate = bidOpenDate; }
    // PUBLIC_INTERFACE
    public BigDecimal getEstimatedCost() { return estimatedCost; }
    // PUBLIC_INTERFACE
    public void setEstimatedCost(BigDecimal estimatedCost) { this.estimatedCost = estimatedCost; }
    // PUBLIC_INTERFACE
    public String getStatus() { return status; }
    // PUBLIC_INTERFACE
    public void setStatus(String status) { this.status = status; }
}
