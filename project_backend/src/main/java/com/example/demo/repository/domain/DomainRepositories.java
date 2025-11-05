package com.example.demo.repository.domain;

import com.example.demo.model.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Grouped JPA repositories for domain entities.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
    // PUBLIC_INTERFACE
    Optional<Project> findByCode(String code);
    // PUBLIC_INTERFACE
    Page<Project> findByDistrictContainingIgnoreCase(String district, Pageable pageable);
}

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    // PUBLIC_INTERFACE
    Page<Milestone> findByProjectId(Long projectId, Pageable pageable);
}

public interface ContractorRepository extends JpaRepository<Contractor, Long> {
    // PUBLIC_INTERFACE
    Optional<Contractor> findByNameIgnoreCase(String name);
}

public interface TenderRepository extends JpaRepository<Tender, Long> {
    // PUBLIC_INTERFACE
    Optional<Tender> findByNumber(String number);
    // PUBLIC_INTERFACE
    Page<Tender> findByProjectId(Long projectId, Pageable pageable);
}

public interface ContractRepository extends JpaRepository<Contract, Long> {
    // PUBLIC_INTERFACE
    Optional<Contract> findByContractNumber(String contractNumber);
    // PUBLIC_INTERFACE
    Page<Contract> findByProjectId(Long projectId, Pageable pageable);
}

public interface InspectionRepository extends JpaRepository<Inspection, Long> {
    // PUBLIC_INTERFACE
    Page<Inspection> findByProjectId(Long projectId, Pageable pageable);
}

public interface HandoverRepository extends JpaRepository<Handover, Long> {
    // PUBLIC_INTERFACE
    Optional<Handover> findByProjectId(Long projectId);
}

public interface FundRepository extends JpaRepository<Fund, Long> {
    // PUBLIC_INTERFACE
    Page<Fund> findByProjectId(Long projectId, Pageable pageable);
}

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // PUBLIC_INTERFACE
    Page<Payment> findByProjectId(Long projectId, Pageable pageable);
}

public interface ProjectDocumentRepository extends JpaRepository<ProjectDocument, Long> {
    // PUBLIC_INTERFACE
    Page<ProjectDocument> findByProjectId(Long projectId, Pageable pageable);
}

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // PUBLIC_INTERFACE
    Page<Notification> findByUsernameOrderByCreatedAtDesc(String username, Pageable pageable);
}
