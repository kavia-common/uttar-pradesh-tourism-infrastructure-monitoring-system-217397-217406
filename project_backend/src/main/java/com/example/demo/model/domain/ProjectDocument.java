package com.example.demo.model.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Metadata for files/documents linked to a project.
 */
@Entity
@Table(name = "project_documents")
public class ProjectDocument {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="project_id")
    private Project project;

    @Column(nullable=false, length=300)
    private String filename;

    @Column(length=100)
    private String contentType;

    @Column(length=500)
    private String path;

    @Column(length=200)
    private String category; // TENDER_DOC, INSPECTION_PHOTO, REPORT, etc.

    private LocalDateTime uploadedAt = LocalDateTime.now();

    public ProjectDocument(){}

    // PUBLIC_INTERFACE
    public Long getId() { return id; }
    // PUBLIC_INTERFACE
    public void setId(Long id) { this.id = id; }
    // PUBLIC_INTERFACE
    public Project getProject() { return project; }
    // PUBLIC_INTERFACE
    public void setProject(Project project) { this.project = project; }
    // PUBLIC_INTERFACE
    public String getFilename() { return filename; }
    // PUBLIC_INTERFACE
    public void setFilename(String filename) { this.filename = filename; }
    // PUBLIC_INTERFACE
    public String getContentType() { return contentType; }
    // PUBLIC_INTERFACE
    public void setContentType(String contentType) { this.contentType = contentType; }
    // PUBLIC_INTERFACE
    public String getPath() { return path; }
    // PUBLIC_INTERFACE
    public void setPath(String path) { this.path = path; }
    // PUBLIC_INTERFACE
    public String getCategory() { return category; }
    // PUBLIC_INTERFACE
    public void setCategory(String category) { this.category = category; }
    // PUBLIC_INTERFACE
    public LocalDateTime getUploadedAt() { return uploadedAt; }
    // PUBLIC_INTERFACE
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
}
