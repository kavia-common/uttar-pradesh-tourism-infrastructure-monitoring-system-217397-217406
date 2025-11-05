package com.example.demo.model.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Milestone associated with a project.
 */
@Entity
@Table(name = "milestones")
public class Milestone {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable=false, length=200)
    private String name;

    @Column(length=1000)
    private String description;

    private LocalDate plannedDate;
    private LocalDate actualDate;

    @Column(precision = 15, scale = 2)
    private BigDecimal plannedAmount;

    @Column(precision = 15, scale = 2)
    private BigDecimal releasedAmount;

    @Column(precision = 5, scale = 2)
    private BigDecimal progressPercent;

    public Milestone() {}

    // PUBLIC_INTERFACE
    public Long getId() { return id; }
    // PUBLIC_INTERFACE
    public void setId(Long id) { this.id = id; }
    // PUBLIC_INTERFACE
    public Project getProject() { return project; }
    // PUBLIC_INTERFACE
    public void setProject(Project project) { this.project = project; }
    // PUBLIC_INTERFACE
    public String getName() { return name; }
    // PUBLIC_INTERFACE
    public void setName(String name) { this.name = name; }
    // PUBLIC_INTERFACE
    public String getDescription() { return description; }
    // PUBLIC_INTERFACE
    public void setDescription(String description) { this.description = description; }
    // PUBLIC_INTERFACE
    public LocalDate getPlannedDate() { return plannedDate; }
    // PUBLIC_INTERFACE
    public void setPlannedDate(LocalDate plannedDate) { this.plannedDate = plannedDate; }
    // PUBLIC_INTERFACE
    public LocalDate getActualDate() { return actualDate; }
    // PUBLIC_INTERFACE
    public void setActualDate(LocalDate actualDate) { this.actualDate = actualDate; }
    // PUBLIC_INTERFACE
    public BigDecimal getPlannedAmount() { return plannedAmount; }
    // PUBLIC_INTERFACE
    public void setPlannedAmount(BigDecimal plannedAmount) { this.plannedAmount = plannedAmount; }
    // PUBLIC_INTERFACE
    public BigDecimal getReleasedAmount() { return releasedAmount; }
    // PUBLIC_INTERFACE
    public void setReleasedAmount(BigDecimal releasedAmount) { this.releasedAmount = releasedAmount; }
    // PUBLIC_INTERFACE
    public BigDecimal getProgressPercent() { return progressPercent; }
    // PUBLIC_INTERFACE
    public void setProgressPercent(BigDecimal progressPercent) { this.progressPercent = progressPercent; }
}
