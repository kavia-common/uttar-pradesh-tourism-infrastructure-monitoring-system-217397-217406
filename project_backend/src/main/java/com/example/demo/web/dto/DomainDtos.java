package com.example.demo.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTOs for domain modules.
 */
public class DomainDtos {

    public static class PageRequest {
        @Min(0) public int page = 0;
        @Min(1) @Max(200) public int size = 20;
    }

    public static class ProjectCreate {
        @NotBlank @Size(max=150) public String code;
        @NotBlank @Size(max=300) public String name;
        @Size(max=2000) public String description;
        public LocalDate startDate;
        public LocalDate endDate;
        @DecimalMin("0.0") public BigDecimal budget;
        @Size(max=100) public String district;
        @Size(max=100) public String location;
        @DecimalMin(value="-90.0", inclusive=true) @DecimalMax(value="90.0", inclusive=true) public Double latitude;
        @DecimalMin(value="-180.0", inclusive=true) @DecimalMax(value="180.0", inclusive=true) public Double longitude;
    }

    public static class ProjectUpdate {
        @Size(max=300) public String name;
        @Size(max=2000) public String description;
        public LocalDate startDate;
        public LocalDate endDate;
        @DecimalMin("0.0") public BigDecimal budget;
        @Size(max=50) public String status;
        @Size(max=100) public String district;
        @Size(max=100) public String location;
        @DecimalMin(value="-90.0", inclusive=true) @DecimalMax(value="90.0", inclusive=true) public Double latitude;
        @DecimalMin(value="-180.0", inclusive=true) @DecimalMax(value="180.0", inclusive=true) public Double longitude;
    }

    public static class ContractorCreate {
        @NotBlank @Size(max=200) public String name;
        @Size(max=200) public String contactPerson;
        @Email @Size(max=200) public String email;
        @Size(max=50) public String phone;
        @Size(max=500) public String address;
    }

    public static class ContractorUpdate {
        @Size(max=200) public String contactPerson;
        @Email @Size(max=200) public String email;
        @Size(max=50) public String phone;
        @Size(max=500) public String address;
        @Size(max=50) public String status;
    }

    public static class TenderCreate {
        @NotNull public Long projectId;
        @NotBlank @Size(max=150) public String number;
        public LocalDate publishDate;
        public LocalDate bidOpenDate;
        @DecimalMin("0.0") public BigDecimal estimatedCost;
    }

    public static class TenderUpdate {
        public LocalDate publishDate;
        public LocalDate bidOpenDate;
        @DecimalMin("0.0") public BigDecimal estimatedCost;
        @Size(max=50) public String status;
    }

    public static class ContractCreate {
        @NotNull public Long projectId;
        @NotNull public Long contractorId;
        @NotBlank @Size(max=150) public String contractNumber;
        public LocalDate awardDate;
        public LocalDate completionDate;
        @DecimalMin("0.0") public BigDecimal contractValue;
    }

    public static class ContractUpdate {
        public LocalDate awardDate;
        public LocalDate completionDate;
        @DecimalMin("0.0") public BigDecimal contractValue;
        @Size(max=50) public String status;
    }

    public static class MilestoneCreate {
        @NotNull public Long projectId;
        @NotBlank @Size(max=200) public String name;
        @Size(max=1000) public String description;
        public LocalDate plannedDate;
        @DecimalMin("0.0") public BigDecimal plannedAmount;
    }

    public static class MilestoneUpdate {
        @Size(max=200) public String name;
        @Size(max=1000) public String description;
        public LocalDate plannedDate;
        public LocalDate actualDate;
        @DecimalMin("0.0") public BigDecimal plannedAmount;
        @DecimalMin("0.0") public BigDecimal releasedAmount;
        @DecimalMin("0.0") @DecimalMax("100.0") public BigDecimal progressPercent;
    }

    public static class InspectionCreate {
        @NotNull public Long projectId;
        public Long milestoneId;
        public LocalDate date;
        @Size(max=500) public String inspectorName;
        @Size(max=2000) public String remarks;
        @Size(max=50) public String status;
    }

    public static class HandoverCreate {
        @NotNull public Long projectId;
        public LocalDate handoverDate;
        @Size(max=200) public String receivedBy;
        @Size(max=2000) public String notes;
    }

    public static class FundCreate {
        @NotNull public Long projectId;
        @NotNull @DecimalMin("0.0") public BigDecimal amount;
        @NotBlank @Pattern(regexp="ALLOCATION|RELEASE") public String type;
        @Size(max=500) public String reference;
    }

    public static class PaymentCreate {
        @NotNull public Long projectId;
        @NotNull public Long contractId;
        public Long milestoneId;
        @NotNull public Long contractorId;
        @NotNull @DecimalMin("0.0") public BigDecimal amount;
        @Size(max=50) public String method;
        @Size(max=200) public String referenceNumber;
    }

    public static class PaymentUpdateStatus {
        @NotBlank @Pattern(regexp="PENDING|APPROVED|PAID|REJECTED") public String status;
    }

    public static class NotificationCreate {
        @NotBlank @Size(max=80) public String username;
        @NotBlank @Size(max=500) public String message;
    }
}
