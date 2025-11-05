package com.example.demo.web;

import com.example.demo.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 * ReportsController exposes read-only CSV reports backed by database views.
 * Endpoints:
 * - GET /reports/project-overview: Streams CSV from v_project_overview
 * - GET /reports/fund-utilization: Streams CSV from v_fund_utilization
 *
 * Security:
 * - RBAC via @PreAuthorize; accessible to roles ADMIN, AUDITOR, VIEWER (mapped as roles)
 *   and any user with REPORT_READ permission.
 *
 * Usage:
 *   Set Authorization: Bearer <token> and download CSV. Optional filters available via query params.
 */
@RestController
@RequestMapping("/reports")
@Tag(name = "Reports", description = "Reporting endpoints that stream CSV content from database views")
public class ReportsController {

    private final ReportService reports;

    public ReportsController(ReportService reports) {
        this.reports = reports;
    }

    /**
     * PUBLIC_INTERFACE
     * Streams project overview CSV based on DB view v_project_overview.
     * Optional filters:
     * - district: ILIKE match (use % for wildcard)
     * - status: ILIKE match (use % for wildcard)
     */
    @GetMapping("/project-overview")
    @PreAuthorize("hasAuthority('REPORT_READ') or hasRole('ADMIN') or hasRole('AUDITOR') or hasRole('VIEWER')")
    @Operation(
            summary = "Project overview report (CSV)",
            description = "Streams CSV data from DB view v_project_overview. Optional filters 'district' and 'status' use ILIKE with optional % wildcards."
    )
    public ResponseEntity<StreamingResponseBody> projectOverviewCsv(
            @Parameter(description = "District filter (ILIKE). Example: %Lucknow%") @RequestParam(value = "district", required = false) String district,
            @Parameter(description = "Status filter (ILIKE). Example: ACTIVE") @RequestParam(value = "status", required = false) String status
    ) {
        return reports.streamProjectOverviewCsv(district, status);
    }

    /**
     * PUBLIC_INTERFACE
     * Streams fund utilization CSV based on DB view v_fund_utilization.
     * Optional filter:
     * - projectCode: ILIKE match (use % for wildcard)
     */
    @GetMapping("/fund-utilization")
    @PreAuthorize("hasAuthority('REPORT_READ') or hasRole('ADMIN') or hasRole('AUDITOR') or hasRole('VIEWER')")
    @Operation(
            summary = "Fund utilization report (CSV)",
            description = "Streams CSV data from DB view v_fund_utilization. Optional filter 'projectCode' uses ILIKE with optional % wildcards."
    )
    public ResponseEntity<StreamingResponseBody> fundUtilizationCsv(
            @Parameter(description = "Project code filter (ILIKE). Example: PRJ-%") @RequestParam(value = "projectCode", required = false) String projectCode
    ) {
        return reports.streamFundUtilizationCsv(projectCode);
    }
}
