package com.example.demo.web;

import com.example.demo.model.domain.Project;
import com.example.demo.model.domain.ProjectDocument;
import com.example.demo.repository.domain.ProjectDocumentRepository;
import com.example.demo.repository.domain.ProjectRepository;
import com.example.demo.service.LocalFileStorageService;
import com.example.demo.web.dto.DocumentDtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

/**
 * Controller for handling project document uploads, downloads, and listing.
 * All endpoints are secured via RBAC using DOCUMENT_READ and DOCUMENT_WRITE permissions or ADMIN role.
 */
@RestController
@RequestMapping("/api/documents")
@Tag(name = "Projects", description = "Project management")
public class DocumentController {

    private final LocalFileStorageService storage;
    private final ProjectRepository projectRepo;
    private final ProjectDocumentRepository docRepo;

    public DocumentController(LocalFileStorageService storage,
                              ProjectRepository projectRepo,
                              ProjectDocumentRepository docRepo) {
        this.storage = storage;
        this.projectRepo = projectRepo;
        this.docRepo = docRepo;
    }

    /**
     * PUBLIC_INTERFACE
     * Upload a document for a project. Persists metadata and stores file on disk.
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('DOCUMENT_WRITE') or hasRole('ADMIN')")
    @Operation(summary = "Upload project document", description = "Upload a file and persist metadata for a given project.")
    public ResponseEntity<DocumentMetadataResponse> upload(
            @RequestPart("meta") @Valid DocumentUploadRequest meta,
            @RequestPart("file") MultipartFile file,
            Authentication auth) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Project project = projectRepo.findById(meta.projectId)
                .orElseThrow(() -> new NoSuchElementException("Project not found"));

        Path savedPath = storage.save(file, meta.projectId);

        ProjectDocument doc = new ProjectDocument();
        doc.setProject(project);
        doc.setFilename(file.getOriginalFilename());
        doc.setContentType(file.getContentType());
        doc.setPath(savedPath.toString());
        doc.setCategory(meta.category);
        doc.setSize(file.getSize());
        doc.setUploaderId(auth != null ? auth.getName() : "system");
        doc.setUploadedAt(LocalDateTime.now());

        ProjectDocument saved = docRepo.save(doc);

        DocumentMetadataResponse resp = toDto(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    /**
     * PUBLIC_INTERFACE
     * Download a document by id.
     */
    @GetMapping("/{id}/download")
    @PreAuthorize("hasAuthority('DOCUMENT_READ') or hasRole('ADMIN')")
    @Operation(summary = "Download document", description = "Download the raw file content for a document by id.")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        ProjectDocument doc = docRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Document not found"));
        Resource resource = storage.loadAsResource(doc.getPath());
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        try {
            if (doc.getContentType() != null) {
                mediaType = MediaType.parseMediaType(doc.getContentType());
            }
        } catch (Exception ignore) {}

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + (doc.getFilename() == null ? "file" : doc.getFilename()) + "\"")
                .contentLength(doc.getSize() == null ? -1 : doc.getSize())
                .body(resource);
    }

    /**
     * PUBLIC_INTERFACE
     * List documents for a project (paginated).
     */
    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasAuthority('DOCUMENT_READ') or hasRole('ADMIN')")
    @Operation(summary = "List project documents", description = "Get paginated list of document metadata for a project.")
    public ResponseEntity<Page<DocumentMetadataResponse>> list(
            @PathVariable Long projectId,
            @Valid DocumentDtos.PageRequest qp) {
        Page<ProjectDocument> page = docRepo.findByProjectId(projectId, PageRequest.of(qp.page, qp.size));
        Page<DocumentMetadataResponse> mapped = page.map(this::toDto);
        return ResponseEntity.ok(mapped);
    }

    private DocumentMetadataResponse toDto(ProjectDocument d) {
        DocumentMetadataResponse resp = new DocumentMetadataResponse();
        resp.id = d.getId();
        resp.filename = d.getFilename();
        resp.contentType = d.getContentType();
        resp.path = d.getPath();
        resp.size = d.getSize();
        resp.uploaderId = d.getUploaderId();
        resp.projectId = d.getProject() != null ? d.getProject().getId() : null;
        resp.category = d.getCategory();
        resp.uploadedAt = d.getUploadedAt();
        return resp;
    }
}
