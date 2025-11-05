package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for persisting uploaded files to the local filesystem.
 * Uses base directory configured via file.upload-dir property.
 */
@Service
public class LocalFileStorageService {

    private final Path baseDir;

    public LocalFileStorageService(@Value("${file.upload-dir}") String uploadDir) throws IOException {
        this.baseDir = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(this.baseDir);
    }

    /**
     * PUBLIC_INTERFACE
     * Save a file under project-specific directory and return the absolute path.
     * The file will be stored at: <base>/<projectId>/<yyyy>/<MM>/<safeFilename>
     */
    public Path save(MultipartFile file, Long projectId) throws IOException {
        String originalName = StringUtils.cleanPath(file.getOriginalFilename() == null ? "file" : file.getOriginalFilename());
        // Prevent path traversal
        originalName = originalName.replace("..", "").replace("/", "_").replace("\\", "_");

        LocalDate now = LocalDate.now();
        Path projectDir = baseDir.resolve(String.valueOf(projectId))
                .resolve(String.valueOf(now.getYear()))
                .resolve(String.format("%02d", now.getMonthValue()));
        Files.createDirectories(projectDir);

        Path target = projectDir.resolve(uniqueName(projectDir, originalName));
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return target.toAbsolutePath().normalize();
    }

    /**
     * PUBLIC_INTERFACE
     * Load a file as a Resource by absolute path string saved in DB.
     */
    public Resource loadAsResource(String absolutePath) {
        Path p = Paths.get(absolutePath);
        return new FileSystemResource(p);
    }

    /**
     * PUBLIC_INTERFACE
     * List all files for a project. Returns absolute paths.
     */
    public List<String> listProjectFiles(Long projectId) throws IOException {
        Path projectPath = baseDir.resolve(String.valueOf(projectId));
        List<String> results = new ArrayList<>();
        if (!Files.exists(projectPath)) return results;

        Files.walk(projectPath)
                .filter(Files::isRegularFile)
                .forEach(fp -> results.add(fp.toAbsolutePath().toString()));
        return results;
    }

    private String uniqueName(Path dir, String original) throws IOException {
        String name = original;
        String base = original;
        String ext = "";
        int dot = original.lastIndexOf('.');
        if (dot > 0) {
            base = original.substring(0, dot);
            ext = original.substring(dot);
        }
        int counter = 0;
        Path candidate = dir.resolve(name);
        while (Files.exists(candidate)) {
            counter++;
            name = base + "-" + counter + ext;
            candidate = dir.resolve(name);
        }
        return name;
    }
}
