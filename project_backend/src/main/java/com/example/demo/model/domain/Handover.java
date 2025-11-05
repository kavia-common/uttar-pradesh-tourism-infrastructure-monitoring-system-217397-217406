package com.example.demo.model.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Handover record after project completion.
 */
@Entity
@Table(name = "handovers")
public class Handover {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="project_id")
    private Project project;

    private LocalDate handoverDate;

    @Column(length=200)
    private String receivedBy;

    @Column(length=2000)
    private String notes;

    public Handover(){}

    // PUBLIC_INTERFACE
    public Long getId() { return id; }
    // PUBLIC_INTERFACE
    public void setId(Long id) { this.id = id; }
    // PUBLIC_INTERFACE
    public Project getProject() { return project; }
    // PUBLIC_INTERFACE
    public void setProject(Project project) { this.project = project; }
    // PUBLIC_INTERFACE
    public LocalDate getHandoverDate() { return handoverDate; }
    // PUBLIC_INTERFACE
    public void setHandoverDate(LocalDate handoverDate) { this.handoverDate = handoverDate; }
    // PUBLIC_INTERFACE
    public String getReceivedBy() { return receivedBy; }
    // PUBLIC_INTERFACE
    public void setReceivedBy(String receivedBy) { this.receivedBy = receivedBy; }
    // PUBLIC_INTERFACE
    public String getNotes() { return notes; }
    // PUBLIC_INTERFACE
    public void setNotes(String notes) { this.notes = notes; }
}
