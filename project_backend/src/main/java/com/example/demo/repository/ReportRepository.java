package com.example.demo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * Repository for reporting database views.
 * Uses native queries against Postgres views:
 * - v_project_overview
 * - v_fund_utilization
 *
 * Note: We intentionally return a generic List<Map<String, Object>> so we can stream CSV
 * without maintaining strict projections here. This keeps it resilient to minor view changes.
 */
public interface ReportRepository extends Repository<Object, Long> {

    /**
     * PUBLIC_INTERFACE
     * Returns rows from v_project_overview limited by filters (optional).
     * Provide optional district or status filters as ILIKE patterns; pass null to ignore.
     */
    @Query(value = """
            SELECT *
            FROM v_project_overview
            WHERE (:district IS NULL OR district ILIKE :district)
              AND (:status IS NULL OR status ILIKE :status)
            """, nativeQuery = true)
    List<Map<String, Object>> getProjectOverview(
            @Param("district") String district,
            @Param("status") String status
    );

    /**
     * PUBLIC_INTERFACE
     * Returns rows from v_fund_utilization limited by filters (optional).
     * Provide optional project_code filter as ILIKE; pass null to ignore.
     */
    @Query(value = """
            SELECT *
            FROM v_fund_utilization
            WHERE (:projectCode IS NULL OR project_code ILIKE :projectCode)
            """, nativeQuery = true)
    List<Map<String, Object>> getFundUtilization(
            @Param("projectCode") String projectCode
    );
}
