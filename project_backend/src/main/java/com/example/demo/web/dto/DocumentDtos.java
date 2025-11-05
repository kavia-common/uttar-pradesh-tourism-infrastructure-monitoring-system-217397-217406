package com.example.demo.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

/**
 * DTOs for document upload and metadata operations.
 */
public class DocumentDtos {

    public static class DocumentUploadRequest {
        @Schema(description = "Project ID to associate the document with", example = "1")
        @NotNull public Long projectId;

        @Schema(description = "Optional category such as TENDER_DOC, INSPECTION_PHOTO, REPORT", example = "REPORT")
        @Size(max = 200) public String category;
    }

    public static class DocumentMetadataResponse {
        @Schema(description = "Document ID")
        public Long id;
        @Schema(description = "Original file name")
        public String filename;
        @Schema(description = "MIME content type")
        public String contentType;
        @Schema(description = "Absolute path on server filesystem")
        public String path;
        @Schema(description = "File size in bytes")
        public Long size;
        @Schema(description = "Uploader username (if available)")
        public String uploaderId;
        @Schema(description = "Project ID")
        public Long projectId;
        @Schema(description = "Category label")
        public String category;
        @Schema(description = "Upload timestamp")
        public LocalDateTime uploadedAt;
    }

    public static class PageRequest {
        @Min(0) public int page = 0;
        @Min(1) @Max(200) public int size = 20;
    }
}
