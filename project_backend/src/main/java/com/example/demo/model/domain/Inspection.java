package com.example.demo.model.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Inspection records against a project/milestone.
 */
@Entity
@Table(name = "inspections")
public class Inspection {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="project_id")
    private Project project;

    @ManyToOne @JoinColumn(name="milestone_id")
    private Milestone milestone;

    private LocalDate date;

    @Column(length=500)
    private String inspectorName;

    @Column(length=2000)
    private String remarks;

    @Column(length=50)
    private String status; // PASSED/FAILED/PENDING

    public Inspection(){}

    // PUBLIC_INTERFACE
    public Long getId() { return id; }
    // PUBLIC_INTERFACE
    public void setId(Long id) { this.id = id; }
    // PUBLIC_INTERFACE
    public Project getProject() { return project; }
    // PUBLIC_INTERFACE
    public void setProject(Project project) { this.project = project; }
    // PUBLIC_INTERFACE
    public Milestone getMilestone() { return milestone; }
    // PUBLIC_INTERFACE
    public void setMilestone(Milestone milestone) { this.milestone = milestone; }
    // PUBLIC_INTERFACE
    public LocalDate getDate() { return date; }
    // PUBLIC_INTERFACE
    public void setDate(LocalDate date) { this.date = date; }
    // PUBLIC_INTERFACE
    public String getInspectorName() { return inspectorName; }
    // PUBLIC_INTERFACE
    public void setInspectorName(String inspectorName) { this.inspectorName = inspectorName; }
    // PUBLIC_INTERFACE
    public String getRemarks() { return remarks; }
    // PUBLIC_INTERFACE
    public void setRemarks(String remarks) { this.remarks = remarks; }
    // PUBLIC_INTERFACE
    public String getStatus() { return status; }
    // PUBLIC_INTERFACE
    public void setStatus(String status) { this.status = status; }
}
