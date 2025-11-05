package com.example.demo.web;

import com.example.demo.model.domain.*;
import com.example.demo.service.domain.DomainServices;
import com.example.demo.web.dto.DomainDtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controllers for domain modules.
 */
@RestController
@RequestMapping("/api")
public class DomainControllers {

    private final DomainServices service;

    public DomainControllers(DomainServices service) {
        this.service = service;
    }

    // PROJECTS
    @PostMapping("/projects")
    @Tag(name="Projects")
    @Operation(summary="Create project", description="Create a new project")
    // PUBLIC_INTERFACE
    public ResponseEntity<Project> createProject(@Valid @RequestBody ProjectCreate dto) {
        return ResponseEntity.ok(service.createProject(dto));
    }

    @GetMapping("/projects")
    @Tag(name="Projects")
    @Operation(summary="List projects", description="Paginated list of projects")
    // PUBLIC_INTERFACE
    public ResponseEntity<Page<Project>> listProjects(@Valid PageRequest qp) {
        return ResponseEntity.ok(service.listProjects(qp.page, qp.size));
    }

    @GetMapping("/projects/{id}")
    @Tag(name="Projects")
    @Operation(summary="Get project", description="Get project by ID")
    // PUBLIC_INTERFACE
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProject(id));
    }

    @PatchMapping("/projects/{id}")
    @Tag(name="Projects")
    @Operation(summary="Update project", description="Partial update of a project")
    // PUBLIC_INTERFACE
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectUpdate dto) {
        return ResponseEntity.ok(service.updateProject(id, dto));
    }

    @DeleteMapping("/projects/{id}")
    @Tag(name="Projects")
    @Operation(summary="Delete project", description="Delete project by ID")
    // PUBLIC_INTERFACE
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        service.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    // CONTRACTORS
    @PostMapping("/contractors")
    @Tag(name="Contractors")
    @Operation(summary="Create contractor", description="Create a contractor")
    // PUBLIC_INTERFACE
    public ResponseEntity<Contractor> createContractor(@Valid @RequestBody ContractorCreate dto) {
        return ResponseEntity.ok(service.createContractor(dto));
    }

    @GetMapping("/contractors")
    @Tag(name="Contractors")
    @Operation(summary="List contractors", description="Paginated list of contractors")
    // PUBLIC_INTERFACE
    public ResponseEntity<Page<Contractor>> listContractors(@Valid PageRequest qp) {
        return ResponseEntity.ok(service.listContractors(qp.page, qp.size));
    }

    @PatchMapping("/contractors/{id}")
    @Tag(name="Contractors")
    @Operation(summary="Update contractor", description="Update contractor details")
    // PUBLIC_INTERFACE
    public ResponseEntity<Contractor> updateContractor(@PathVariable Long id, @Valid @RequestBody ContractorUpdate dto) {
        return ResponseEntity.ok(service.updateContractor(id, dto));
    }

    // TENDERS
    @PostMapping("/tenders")
    @Tag(name="Tenders")
    @Operation(summary="Create tender", description="Create a tender for a project")
    // PUBLIC_INTERFACE
    public ResponseEntity<Tender> createTender(@Valid @RequestBody TenderCreate dto) {
        return ResponseEntity.ok(service.createTender(dto));
    }

    @GetMapping("/projects/{projectId}/tenders")
    @Tag(name="Tenders")
    @Operation(summary="List tenders for project", description="Paginated list")
    // PUBLIC_INTERFACE
    public ResponseEntity<Page<Tender>> listTenders(@PathVariable Long projectId, @Valid PageRequest qp) {
        return ResponseEntity.ok(service.listProjectTenders(projectId, qp.page, qp.size));
    }

    @PatchMapping("/tenders/{id}")
    @Tag(name="Tenders")
    @Operation(summary="Update tender", description="Update tender details/status")
    // PUBLIC_INTERFACE
    public ResponseEntity<Tender> updateTender(@PathVariable Long id, @Valid @RequestBody TenderUpdate dto) {
        return ResponseEntity.ok(service.updateTender(id, dto));
    }

    // CONTRACTS
    @PostMapping("/contracts")
    @Tag(name="Contracts")
    @Operation(summary="Create contract", description="Create a contract for a project and contractor")
    // PUBLIC_INTERFACE
    public ResponseEntity<Contract> createContract(@Valid @RequestBody ContractCreate dto) {
        return ResponseEntity.ok(service.createContract(dto));
    }

    @GetMapping("/projects/{projectId}/contracts")
    @Tag(name="Contracts")
    @Operation(summary="List contracts for project", description="Paginated list")
    // PUBLIC_INTERFACE
    public ResponseEntity<Page<Contract>> listContracts(@PathVariable Long projectId, @Valid PageRequest qp) {
        return ResponseEntity.ok(service.listProjectContracts(projectId, qp.page, qp.size));
    }

    @PatchMapping("/contracts/{id}")
    @Tag(name="Contracts")
    @Operation(summary="Update contract", description="Update contract details/status")
    // PUBLIC_INTERFACE
    public ResponseEntity<Contract> updateContract(@PathVariable Long id, @Valid @RequestBody ContractUpdate dto) {
        return ResponseEntity.ok(service.updateContract(id, dto));
    }

    // MILESTONES
    @PostMapping("/milestones")
    @Tag(name="Milestones")
    @Operation(summary="Create milestone", description="Create project milestone")
    // PUBLIC_INTERFACE
    public ResponseEntity<Milestone> createMilestone(@Valid @RequestBody MilestoneCreate dto) {
        return ResponseEntity.ok(service.createMilestone(dto));
    }

    @GetMapping("/projects/{projectId}/milestones")
    @Tag(name="Milestones")
    @Operation(summary="List milestones for project", description="Paginated list")
    // PUBLIC_INTERFACE
    public ResponseEntity<Page<Milestone>> listMilestones(@PathVariable Long projectId, @Valid PageRequest qp) {
        return ResponseEntity.ok(service.listMilestones(projectId, qp.page, qp.size));
    }

    @PatchMapping("/milestones/{id}")
    @Tag(name="Milestones")
    @Operation(summary="Update milestone", description="Update milestone details/status")
    // PUBLIC_INTERFACE
    public ResponseEntity<Milestone> updateMilestone(@PathVariable Long id, @Valid @RequestBody MilestoneUpdate dto) {
        return ResponseEntity.ok(service.updateMilestone(id, dto));
    }

    // INSPECTIONS
    @PostMapping("/inspections")
    @Tag(name="Inspections")
    @Operation(summary="Create inspection", description="Record an inspection")
    // PUBLIC_INTERFACE
    public ResponseEntity<Inspection> createInspection(@Valid @RequestBody InspectionCreate dto) {
        return ResponseEntity.ok(service.createInspection(dto));
    }

    // HANDOVER
    @PostMapping("/handovers")
    @Tag(name="Handovers")
    @Operation(summary="Record handover", description="Record project handover and mark project completed")
    // PUBLIC_INTERFACE
    public ResponseEntity<Handover> recordHandover(@Valid @RequestBody HandoverCreate dto) {
        return ResponseEntity.ok(service.recordHandover(dto));
    }

    // FUNDS
    @PostMapping("/funds")
    @Tag(name="Funds")
    @Operation(summary="Create fund entry", description="Record allocation/release of funds for a project")
    // PUBLIC_INTERFACE
    public ResponseEntity<Fund> createFund(@Valid @RequestBody FundCreate dto) {
        return ResponseEntity.ok(service.createFund(dto));
    }

    // PAYMENTS
    @PostMapping("/payments")
    @Tag(name="Payments")
    @Operation(summary="Create payment", description="Create a payment to a contractor against contract/milestone")
    // PUBLIC_INTERFACE
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody PaymentCreate dto) {
        return ResponseEntity.ok(service.createPayment(dto));
    }

    @PatchMapping("/payments/{id}/status")
    @Tag(name="Payments")
    @Operation(summary="Update payment status", description="Approve, pay, reject a payment")
    // PUBLIC_INTERFACE
    public ResponseEntity<Payment> updatePaymentStatus(@PathVariable Long id, @Valid @RequestBody PaymentUpdateStatus dto) {
        return ResponseEntity.ok(service.updatePaymentStatus(id, dto));
    }

    // NOTIFICATIONS
    @GetMapping("/notifications/{username}")
    @Tag(name="Notifications")
    @Operation(summary="List notifications", description="Get notifications for a username")
    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('REPORT_READ') or hasRole('ADMIN') or #username == authentication.name")
    public ResponseEntity<Page<Notification>> getNotifications(@PathVariable String username, @Valid PageRequest qp) {
        return ResponseEntity.ok(service.getUserNotifications(username, qp.page, qp.size));
    }

    @PostMapping("/notifications")
    @Tag(name="Notifications")
    @Operation(summary="Send notification", description="Admin sends a notification to a user")
    // PUBLIC_INTERFACE
    public ResponseEntity<Notification> sendNotification(@Valid @RequestBody NotificationCreate dto) {
        return ResponseEntity.ok(service.sendNotification(dto));
    }
}
