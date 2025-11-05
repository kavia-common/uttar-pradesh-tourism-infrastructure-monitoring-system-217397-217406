package com.example.demo.model.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Project represents an infrastructure project.
 */
@Entity
@Table(name = "projects")
public class Project {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=150)
    private String code;

    @Column(nullable=false, length=300)
    private String name;

    @Column(length=2000)
    private String description;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(precision = 15, scale = 2)
    private BigDecimal budget;

    @Column(length=50)
    private String status; // PLANNED, ACTIVE, COMPLETED, ON_HOLD, CANCELLED

    @Column(length=100)
    private String district;

    @Column(length=100)
    private String location; // free-text; could be address/landmark

    private Double latitude;
    private Double longitude;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Milestone> milestones = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectDocument> documents = new HashSet<>();

    public Project() {}

    public Project(String code, String name) {
        this.code = code;
        this.name = name;
        this.status = "PLANNED";
    }

    // PUBLIC_INTERFACE
    public Long getId() { return id; }
    // PUBLIC_INTERFACE
    public void setId(Long id) { this.id = id; }
    // PUBLIC_INTERFACE
    public String getCode() { return code; }
    // PUBLIC_INTERFACE
    public void setCode(String code) { this.code = code; }
    // PUBLIC_INTERFACE
    public String getName() { return name; }
    // PUBLIC_INTERFACE
    public void setName(String name) { this.name = name; }
    // PUBLIC_INTERFACE
    public String getDescription() { return description; }
    // PUBLIC_INTERFACE
    public void setDescription(String description) { this.description = description; }
    // PUBLIC_INTERFACE
    public LocalDate getStartDate() { return startDate; }
    // PUBLIC_INTERFACE
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    // PUBLIC_INTERFACE
    public LocalDate getEndDate() { return endDate; }
    // PUBLIC_INTERFACE
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    // PUBLIC_INTERFACE
    public BigDecimal getBudget() { return budget; }
    // PUBLIC_INTERFACE
    public void setBudget(BigDecimal budget) { this.budget = budget; }
    // PUBLIC_INTERFACE
    public String getStatus() { return status; }
    // PUBLIC_INTERFACE
    public void setStatus(String status) { this.status = status; }
    // PUBLIC_INTERFACE
    public String getDistrict() { return district; }
    // PUBLIC_INTERFACE
    public void setDistrict(String district) { this.district = district; }
    // PUBLIC_INTERFACE
    public String getLocation() { return location; }
    // PUBLIC_INTERFACE
    public void setLocation(String location) { this.location = location; }
    // PUBLIC_INTERFACE
    public Double getLatitude() { return latitude; }
    // PUBLIC_INTERFACE
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    // PUBLIC_INTERFACE
    public Double getLongitude() { return longitude; }
    // PUBLIC_INTERFACE
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    // PUBLIC_INTERFACE
    public Set<Milestone> getMilestones() { return milestones; }
    // PUBLIC_INTERFACE
    public void setMilestones(Set<Milestone> milestones) { this.milestones = milestones; }
    // PUBLIC_INTERFACE
    public Set<ProjectDocument> getDocuments() { return documents; }
    // PUBLIC_INTERFACE
    public void setDocuments(Set<ProjectDocument> documents) { this.documents = documents; }
}
