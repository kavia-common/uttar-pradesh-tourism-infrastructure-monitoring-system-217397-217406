package com.example.demo.service;

import com.example.demo.repository.ReportRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Service for generating CSV reports backed by database views.
 * Converts a List<Map<String,Object>> into CSV in a streaming fashion.
 */
@Service
public class ReportService {

    private final ReportRepository repo;

    public ReportService(ReportRepository repo) {
        this.repo = repo;
    }

    /**
     * PUBLIC_INTERFACE
     * Stream CSV for v_project_overview with optional filters.
     * district and status support "%" wildcards since we pass to ILIKE.
     */
    public ResponseEntity<StreamingResponseBody> streamProjectOverviewCsv(String district, String status) {
        String districtParam = district == null || district.isBlank() ? null : district;
        String statusParam = status == null || status.isBlank() ? null : status;

        List<Map<String, Object>> rows = repo.getProjectOverview(districtParam, statusParam);
        return toCsvStreamingResponse("project_overview.csv", rows);
    }

    /**
     * PUBLIC_INTERFACE
     * Stream CSV for v_fund_utilization with optional filter by project_code (supports wildcards).
     */
    public ResponseEntity<StreamingResponseBody> streamFundUtilizationCsv(String projectCode) {
        String projectCodeParam = projectCode == null || projectCode.isBlank() ? null : projectCode;
        List<Map<String, Object>> rows = repo.getFundUtilization(projectCodeParam);
        return toCsvStreamingResponse("fund_utilization.csv", rows);
    }

    private ResponseEntity<StreamingResponseBody> toCsvStreamingResponse(String filename, List<Map<String, Object>> rows) {
        StreamingResponseBody stream = outputStream -> {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
                if (rows == null || rows.isEmpty()) {
                    // no rows: write empty file
                    writer.flush();
                    return;
                }
                // determine headers from first row's keys in deterministic order
                Set<String> headerSet = rows.get(0).keySet();
                List<String> headers = new ArrayList<>(headerSet);
                // sort headers to keep stable column order
                Collections.sort(headers, String.CASE_INSENSITIVE_ORDER);

                // write header
                writeCsvRow(writer, headers);

                // write rows
                for (Map<String, Object> row : rows) {
                    List<String> values = new ArrayList<>(headers.size());
                    for (String h : headers) {
                        Object val = row.get(h);
                        values.add(formatValue(val));
                    }
                    writeCsvRow(writer, values);
                }
                writer.flush();
            }
        };

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/csv"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(stream);
    }

    private void writeCsvRow(OutputStreamWriter writer, List<String> columns) throws Exception {
        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) writer.write(',');
            writer.write(escapeCsv(columns.get(i)));
        }
        writer.write("\r\n");
    }

    private String formatValue(Object val) {
        if (val == null) return "";
        if (val instanceof Date d) {
            return d.toInstant().toString();
        }
        if (val instanceof java.time.temporal.TemporalAccessor ta) {
            // ISO-8601 for temporal types
            try {
                return DateTimeFormatter.ISO_DATE_TIME.format(ta);
            } catch (Exception ignore) {
                try {
                    return DateTimeFormatter.ISO_DATE.format(ta);
                } catch (Exception ignore2) {
                    return val.toString();
                }
            }
        }
        return String.valueOf(val);
    }

    // RFC4180 compatible escaping: double quotes around fields containing special chars, escape quotes by doubling
    private String escapeCsv(String field) {
        if (field == null) return "";
        boolean hasSpecial = field.contains(",") || field.contains("\"") || field.contains("\r") || field.contains("\n");
        String out = field.replace("\"", "\"\"");
        return hasSpecial ? "\"" + out + "\"" : out;
    }
}
