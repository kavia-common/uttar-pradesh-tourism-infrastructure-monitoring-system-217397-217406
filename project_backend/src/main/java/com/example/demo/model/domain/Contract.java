package com.example.demo.model.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Awarded contract between a contractor and a project.
 */
@Entity
@Table(name = "contracts")
public class Contract {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="project_id")
    private Project project;

    @ManyToOne(optional=false) @JoinColumn(name="contractor_id")
    private Contractor contractor;

    @Column(nullable=false, unique=true, length=150)
    private String contractNumber;

    private LocalDate awardDate;
    private LocalDate completionDate;

    @Column(precision = 15, scale = 2)
    private BigDecimal contractValue;

    @Column(length=50)
    private String status; // ACTIVE, COMPLETED, TERMINATED

    public Contract(){}

    // PUBLIC_INTERFACE
    public Long getId() { return id; }
    // PUBLIC_INTERFACE
    public void setId(Long id) { this.id = id; }
    // PUBLIC_INTERFACE
    public Project getProject() { return project; }
    // PUBLIC_INTERFACE
    public void setProject(Project project) { this.project = project; }
    // PUBLIC_INTERFACE
    public Contractor getContractor() { return contractor; }
    // PUBLIC_INTERFACE
    public void setContractor(Contractor contractor) { this.contractor = contractor; }
    // PUBLIC_INTERFACE
    public String getContractNumber() { return contractNumber; }
    // PUBLIC_INTERFACE
    public void setContractNumber(String contractNumber) { this.contractNumber = contractNumber; }
    // PUBLIC_INTERFACE
    public LocalDate getAwardDate() { return awardDate; }
    // PUBLIC_INTERFACE
    public void setAwardDate(LocalDate awardDate) { this.awardDate = awardDate; }
    // PUBLIC_INTERFACE
    public LocalDate getCompletionDate() { return completionDate; }
    // PUBLIC_INTERFACE
    public void setCompletionDate(LocalDate completionDate) { this.completionDate = completionDate; }
    // PUBLIC_INTERFACE
    public BigDecimal getContractValue() { return contractValue; }
    // PUBLIC_INTERFACE
    public void setContractValue(BigDecimal contractValue) { this.contractValue = contractValue; }
    // PUBLIC_INTERFACE
    public String getStatus() { return status; }
    // PUBLIC_INTERFACE
    public void setStatus(String status) { this.status = status; }
}
