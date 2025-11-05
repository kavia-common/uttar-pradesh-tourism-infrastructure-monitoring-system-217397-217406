package com.example.demo.service.domain;

import com.example.demo.model.domain.*;
import com.example.demo.repository.domain.*;
import com.example.demo.service.GeoService;
import com.example.demo.web.dto.DomainDtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * Service classes for domain modules.
 */
@Service
public class DomainServices {

    private final ProjectRepository projectRepo;
    private final MilestoneRepository milestoneRepo;
    private final ContractorRepository contractorRepo;
    private final TenderRepository tenderRepo;
    private final ContractRepository contractRepo;
    private final InspectionRepository inspectionRepo;
    private final HandoverRepository handoverRepo;
    private final FundRepository fundRepo;
    private final PaymentRepository paymentRepo;
    private final ProjectDocumentRepository docRepo;
    private final NotificationRepository notificationRepo;
    private final GeoService geoService;

    public DomainServices(ProjectRepository projectRepo,
                          MilestoneRepository milestoneRepo,
                          ContractorRepository contractorRepo,
                          TenderRepository tenderRepo,
                          ContractRepository contractRepo,
                          InspectionRepository inspectionRepo,
                          HandoverRepository handoverRepo,
                          FundRepository fundRepo,
                          PaymentRepository paymentRepo,
                          ProjectDocumentRepository docRepo,
                          NotificationRepository notificationRepo,
                          GeoService geoService) {
        this.projectRepo = projectRepo;
        this.milestoneRepo = milestoneRepo;
        this.contractorRepo = contractorRepo;
        this.tenderRepo = tenderRepo;
        this.contractRepo = contractRepo;
        this.inspectionRepo = inspectionRepo;
        this.handoverRepo = handoverRepo;
        this.fundRepo = fundRepo;
        this.paymentRepo = paymentRepo;
        this.docRepo = docRepo;
        this.notificationRepo = notificationRepo;
        this.geoService = geoService;
    }

    // PROJECTS

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('PROJECT_WRITE') or hasRole('ADMIN')")
    @Transactional
    public Project createProject(ProjectCreate dto) {
        Project p = new Project(dto.code, dto.name);
        p.setDescription(dto.description);
        p.setStartDate(dto.startDate);
        p.setEndDate(dto.endDate);
        p.setBudget(dto.budget);
        p.setDistrict(dto.district);
        p.setLocation(dto.location);
        p.setLatitude(dto.latitude);
        p.setLongitude(dto.longitude);
        return projectRepo.save(p);
    }

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('PROJECT_READ') or hasRole('ADMIN')")
    public Page<Project> listProjects(int page, int size) {
        return projectRepo.findAll(PageRequest.of(page, size));
    }

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('PROJECT_READ') or hasRole('ADMIN')")
    public Project getProject(Long id) {
        return projectRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Project not found"));
    }

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('PROJECT_WRITE') or hasRole('ADMIN')")
    @Transactional
    public Project updateProject(Long id, ProjectUpdate dto) {
        Project p = getProject(id);
        if (dto.getClass() != null) { /* placeholder to satisfy style */ }
        if (dto.name != null) p.setName(dto.name);
        if (dto.description != null) p.setDescription(dto.description);
        if (dto.startDate != null) p.setStartDate(dto.startDate);
        if (dto.endDate != null) p.setEndDate(dto.endDate);
        if (dto.budget != null) p.setBudget(dto.budget);
        if (dto.status != null) p.setStatus(dto.status);
        if (dto.district != null) p.setDistrict(dto.district);
        if (dto.location != null) p.setLocation(dto.location);
        if (dto.latitude != null) p.setLatitude(dto.latitude);
        if (dto.longitude != null) p.setLongitude(dto.longitude);
        return projectRepo.save(p);
    }

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('PROJECT_WRITE') or hasRole('ADMIN')")
    public void deleteProject(Long id) {
        projectRepo.deleteById(id);
    }

    // CONTRACTORS

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('CONTRACTOR_WRITE') or hasRole('ADMIN')")
    public Contractor createContractor(ContractorCreate dto) {
        Contractor c = new Contractor();
        c.setName(dto.name);
        c.setContactPerson(dto.contactPerson);
        c.setEmail(dto.email);
        c.setPhone(dto.phone);
        c.setAddress(dto.address);
        c.setStatus("ACTIVE");
        return contractorRepo.save(c);
    }

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('CONTRACTOR_READ') or hasRole('ADMIN')")
    public Page<Contractor> listContractors(int page, int size) {
        return contractorRepo.findAll(PageRequest.of(page, size));
    }

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('CONTRACTOR_WRITE') or hasRole('ADMIN')")
    public Contractor updateContractor(Long id, ContractorUpdate dto) {
        Contractor c = contractorRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Contractor not found"));
        if (dto.contactPerson != null) c.setContactPerson(dto.contactPerson);
        if (dto.email != null) c.setEmail(dto.email);
        if (dto.phone != null) c.setPhone(dto.phone);
        if (dto.address != null) c.setAddress(dto.address);
        if (dto.status != null) c.setStatus(dto.status);
        return contractorRepo.save(c);
    }

    // TENDERS

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('TENDER_WRITE') or hasRole('ADMIN')")
    public Tender createTender(TenderCreate dto) {
        Project project = getProject(dto.projectId);
        Tender t = new Tender();
        t.setProject(project);
        t.setNumber(dto.number);
        t.setPublishDate(dto.publishDate);
        t.setBidOpenDate(dto.bidOpenDate);
        t.setEstimatedCost(dto.estimatedCost);
        t.setStatus("PUBLISHED");
        return tenderRepo.save(t);
    }

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('TENDER_READ') or hasRole('ADMIN')")
    public Page<Tender> listProjectTenders(Long projectId, int page, int size) {
        return tenderRepo.findByProjectId(projectId, PageRequest.of(page, size));
    }

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('TENDER_WRITE') or hasRole('ADMIN')")
    public Tender updateTender(Long id, TenderUpdate dto) {
        Tender t = tenderRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Tender not found"));
        if (dto.publishDate != null) t.setPublishDate(dto.publishDate);
        if (dto.bidOpenDate != null) t.setBidOpenDate(dto.bidOpenDate);
        if (dto.estimatedCost != null) t.setEstimatedCost(dto.estimatedCost);
        if (dto.status != null) t.setStatus(dto.status);
        return tenderRepo.save(t);
    }

    // CONTRACTS

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('PROJECT_WRITE') or hasRole('ADMIN')")
    public Contract createContract(ContractCreate dto) {
        Project project = getProject(dto.projectId);
        Contractor contractor = contractorRepo.findById(dto.contractorId)
                .orElseThrow(() -> new NoSuchElementException("Contractor not found"));
        Contract c = new Contract();
        c.setProject(project);
        c.setContractor(contractor);
        c.setContractNumber(dto.contractNumber);
        c.setAwardDate(dto.awardDate);
        c.setCompletionDate(dto.completionDate);
        c.setContractValue(dto.contractValue);
        c.setStatus("ACTIVE");
        return contractRepo.save(c);
    }

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('PROJECT_READ') or hasRole('ADMIN')")
    public Page<Contract> listProjectContracts(Long projectId, int page, int size) {
        return contractRepo.findByProjectId(projectId, PageRequest.of(page, size));
    }

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('PROJECT_WRITE') or hasRole('ADMIN')")
    public Contract updateContract(Long id, ContractUpdate dto) {
        Contract c = contractRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Contract not found"));
        if (dto.awardDate != null) c.setAwardDate(dto.awardDate);
        if (dto.completionDate != null) c.setCompletionDate(dto.completionDate);
        if (dto.contractValue != null) c.setContractValue(dto.contractValue);
        if (dto.status != null) c.setStatus(dto.status);
        return contractRepo.save(c);
    }

    // MILESTONES

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('MILESTONE_WRITE') or hasRole('ADMIN')")
    public Milestone createMilestone(MilestoneCreate dto) {
        Project p = getProject(dto.projectId);
        Milestone m = new Milestone();
        m.setProject(p);
        m.setName(dto.name);
        m.setDescription(dto.description);
        m.setPlannedDate(dto.plannedDate);
        m.setPlannedAmount(dto.plannedAmount);
        return milestoneRepo.save(m);
    }

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('MILESTONE_READ') or hasRole('ADMIN')")
    public Page<Milestone> listMilestones(Long projectId, int page, int size) {
        return milestoneRepo.findByProjectId(projectId, PageRequest.of(page, size));
    }

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('MILESTONE_WRITE') or hasRole('ADMIN')")
    public Milestone updateMilestone(Long id, MilestoneUpdate dto) {
        Milestone m = milestoneRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Milestone not found"));
        if (dto.name != null) m.setName(dto.name);
        if (dto.description != null) m.setDescription(dto.description);
        if (dto.plannedDate != null) m.setPlannedDate(dto.plannedDate);
        if (dto.actualDate != null) m.setActualDate(dto.actualDate);
        if (dto.plannedAmount != null) m.setPlannedAmount(dto.plannedAmount);
        if (dto.releasedAmount != null) m.setReleasedAmount(dto.releasedAmount);
        if (dto.progressPercent != null) m.setProgressPercent(dto.progressPercent);
        return milestoneRepo.save(m);
    }

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('MILESTONE_WRITE') or hasRole('ADMIN')")
    @Transactional
    public Milestone updateMilestoneProgressWithGeo(Long milestoneId, MilestoneProgressUpdate dto) {
        Milestone m = milestoneRepo.findById(milestoneId).orElseThrow(() -> new NoSuchElementException("Milestone not found"));
        if (dto.progressPercent != null) m.setProgressPercent(dto.progressPercent);
        if (dto.latitude != null) m.setProgressLatitude(dto.latitude);
        if (dto.longitude != null) m.setProgressLongitude(dto.longitude);

        String note = dto.locationNote;
        if ((note == null || note.isBlank()) && dto.latitude != null && dto.longitude != null) {
            String reversed = geoService.reverseGeocode(dto.latitude, dto.longitude);
            if (reversed != null && !reversed.isBlank()) {
                note = reversed;
            }
        }
        if (note != null) m.setProgressLocationNote(note);
        return milestoneRepo.save(m);
    }

    // INSPECTIONS

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('PROJECT_WRITE') or hasRole('ADMIN')")
    public Inspection createInspection(InspectionCreate dto) {
        Project p = getProject(dto.projectId);
        Inspection i = new Inspection();
        i.setProject(p);
        if (dto.milestoneId != null) {
            Milestone m = milestoneRepo.findById(dto.milestoneId)
                    .orElseThrow(() -> new NoSuchElementException("Milestone not found"));
            i.setMilestone(m);
        }
        i.setDate(dto.date);
        i.setInspectorName(dto.inspectorName);
        i.setRemarks(dto.remarks);
        i.setStatus(dto.status == null ? "PENDING" : dto.status);

        // Geo
        i.setLatitude(dto.latitude);
        i.setLongitude(dto.longitude);
        String locationText = dto.locationText;
        if ((locationText == null || locationText.isBlank()) && dto.latitude != null && dto.longitude != null) {
            String reversed = geoService.reverseGeocode(dto.latitude, dto.longitude);
            if (reversed != null && !reversed.isBlank()) {
                locationText = reversed;
            }
        }
        i.setLocationText(locationText);

        return inspectionRepo.save(i);
    }

    // HANDOVER

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('PROJECT_WRITE') or hasRole('ADMIN')")
    public Handover recordHandover(HandoverCreate dto) {
        Project p = getProject(dto.projectId);
        Handover h = handoverRepo.findByProjectId(p.getId()).orElse(new Handover());
        h.setProject(p);
        h.setHandoverDate(dto.handoverDate);
        h.setReceivedBy(dto.receivedBy);
        h.setNotes(dto.notes);
        // mark project completed
        p.setStatus("COMPLETED");
        projectRepo.save(p);
        return handoverRepo.save(h);
    }

    // FUNDS

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('PAYMENT_WRITE') or hasRole('ADMIN')")
    public Fund createFund(FundCreate dto) {
        Project p = getProject(dto.projectId);
        Fund f = new Fund();
        f.setProject(p);
        f.setAmount(dto.amount);
        f.setType(dto.type);
        f.setReference(dto.reference);
        return fundRepo.save(f);
    }

    // PAYMENTS

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('PAYMENT_WRITE') or hasRole('ADMIN')")
    public Payment createPayment(PaymentCreate dto) {
        Project p = getProject(dto.projectId);
        Contract c = contractRepo.findById(dto.contractId).orElseThrow(() -> new NoSuchElementException("Contract not found"));
        Contractor contractor = contractorRepo.findById(dto.contractorId).orElseThrow(() -> new NoSuchElementException("Contractor not found"));
        Payment pay = new Payment();
        pay.setProject(p);
        pay.setContract(c);
        if (dto.milestoneId != null) {
            Milestone m = milestoneRepo.findById(dto.milestoneId)
                    .orElseThrow(() -> new NoSuchElementException("Milestone not found"));
            pay.setMilestone(m);
        }
        pay.setContractor(contractor);
        pay.setAmount(dto.amount);
        pay.setMethod(dto.method);
        pay.setReferenceNumber(dto.referenceNumber);
        pay.setStatus("PENDING");
        return paymentRepo.save(pay);
    }

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('PAYMENT_WRITE') or hasRole('ADMIN')")
    @Transactional
    public Payment updatePaymentStatus(Long paymentId, PaymentUpdateStatus dto) {
        Payment p = paymentRepo.findById(paymentId).orElseThrow(() -> new NoSuchElementException("Payment not found"));
        p.setStatus(dto.status);
        return paymentRepo.save(p);
    }

    // NOTIFICATIONS

    // PUBLIC_INTERFACE
    @PreAuthorize("hasAuthority('REPORT_READ') or hasRole('ADMIN')")
    public Page<Notification> getUserNotifications(String username, int page, int size) {
        return notificationRepo.findByUsernameOrderByCreatedAtDesc(username, PageRequest.of(page, size));
    }

    // PUBLIC_INTERFACE
    @PreAuthorize("hasRole('ADMIN')") // only admins can send system notifications in this basic version
    public Notification sendNotification(NotificationCreate dto) {
        Notification n = new Notification();
        n.setUsername(dto.username);
        n.setMessage(dto.message);
        return notificationRepo.save(n);
    }
}
