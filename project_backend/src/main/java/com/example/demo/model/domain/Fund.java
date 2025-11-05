package com.example.demo.model.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Fund allocation/release record for a project.
 */
@Entity
@Table(name = "funds")
public class Fund {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="project_id")
    private Project project;

    private LocalDate date;

    @Column(precision = 15, scale = 2, nullable=false)
    private BigDecimal amount;

    @Column(length=50)
    private String type; // ALLOCATION or RELEASE

    @Column(length=500)
    private String reference;

    public Fund(){}

    // PUBLIC_INTERFACE
    public Long getId() { return id; }
    // PUBLIC_INTERFACE
    public void setId(Long id) { this.id = id; }
    // PUBLIC_INTERFACE
    public Project getProject() { return project; }
    // PUBLIC_INTERFACE
    public void setProject(Project project) { this.project = project; }
    // PUBLIC_INTERFACE
    public LocalDate getDate() { return date; }
    // PUBLIC_INTERFACE
    public void setDate(LocalDate date) { this.date = date; }
    // PUBLIC_INTERFACE
    public BigDecimal getAmount() { return amount; }
    // PUBLIC_INTERFACE
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    // PUBLIC_INTERFACE
    public String getType() { return type; }
    // PUBLIC_INTERFACE
    public void setType(String type) { this.type = type; }
    // PUBLIC_INTERFACE
    public String getReference() { return reference; }
    // PUBLIC_INTERFACE
    public void setReference(String reference) { this.reference = reference; }
}
