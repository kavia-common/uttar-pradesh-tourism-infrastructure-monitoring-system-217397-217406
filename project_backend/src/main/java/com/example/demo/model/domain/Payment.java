package com.example.demo.model.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Payment made to a contractor against a project/contract/milestone.
 */
@Entity
@Table(name = "payments")
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="project_id")
    private Project project;

    @ManyToOne(optional=false) @JoinColumn(name="contract_id")
    private Contract contract;

    @ManyToOne @JoinColumn(name="milestone_id")
    private Milestone milestone;

    @ManyToOne(optional=false) @JoinColumn(name="contractor_id")
    private Contractor contractor;

    private LocalDate date;

    @Column(precision = 15, scale = 2, nullable=false)
    private BigDecimal amount;

    @Column(length=50)
    private String status; // PENDING, APPROVED, PAID, REJECTED

    @Column(length=200)
    private String method; // NEFT/RTGS/UPI/etc.

    @Column(length=200)
    private String referenceNumber;

    public Payment(){}

    // PUBLIC_INTERFACE
    public Long getId() { return id; }
    // PUBLIC_INTERFACE
    public void setId(Long id) { this.id = id; }
    // PUBLIC_INTERFACE
    public Project getProject() { return project; }
    // PUBLIC_INTERFACE
    public void setProject(Project project) { this.project = project; }
    // PUBLIC_INTERFACE
    public Contract getContract() { return contract; }
    // PUBLIC_INTERFACE
    public void setContract(Contract contract) { this.contract = contract; }
    // PUBLIC_INTERFACE
    public Milestone getMilestone() { return milestone; }
    // PUBLIC_INTERFACE
    public void setMilestone(Milestone milestone) { this.milestone = milestone; }
    // PUBLIC_INTERFACE
    public Contractor getContractor() { return contractor; }
    // PUBLIC_INTERFACE
    public void setContractor(Contractor contractor) { this.contractor = contractor; }
    // PUBLIC_INTERFACE
    public LocalDate getDate() { return date; }
    // PUBLIC_INTERFACE
    public void setDate(LocalDate date) { this.date = date; }
    // PUBLIC_INTERFACE
    public BigDecimal getAmount() { return amount; }
    // PUBLIC_INTERFACE
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    // PUBLIC_INTERFACE
    public String getStatus() { return status; }
    // PUBLIC_INTERFACE
    public void setStatus(String status) { this.status = status; }
    // PUBLIC_INTERFACE
    public String getMethod() { return method; }
    // PUBLIC_INTERFACE
    public void setMethod(String method) { this.method = method; }
    // PUBLIC_INTERFACE
    public String getReferenceNumber() { return referenceNumber; }
    // PUBLIC_INTERFACE
    public void setReferenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; }
}
