package com.infinotive.facility.domain.service;

import com.infinotive.facility.config.StorageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * Handles low-level file storage on disk.
 */
@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final StorageProperties storageProperties;

    public StoredFileResult store(String tenantId, String issueRef, MultipartFile file) throws IOException {
        validateSize(file);

        String originalFilename = file.getOriginalFilename();
        String extension = extractExtension(originalFilename);
        String storedFilename = UUID.randomUUID() + (extension != null ? "." + extension : "");

        // relative: tenantId/issueRef/filename
        String relativePath = tenantId + "/" + issueRef + "/" + storedFilename;

        Path baseDir = Path.of(storageProperties.getBasePath()).toAbsolutePath().normalize();
        Path targetPath = baseDir.resolve(relativePath).normalize();

        Files.createDirectories(targetPath.getParent());
        file.transferTo(targetPath.toFile());

        return new StoredFileResult(
                originalFilename,
                storedFilename,
                file.getContentType(),
                file.getSize(),
                relativePath
        );
    }

    private void validateSize(MultipartFile file) {
        long maxBytes = (long) storageProperties.getMaxFileSizeMb() * 1024 * 1024;
        if (file.getSize() > maxBytes) {
            throw new IllegalArgumentException("File too large");
        }
    }

    private String extractExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int idx = filename.lastIndexOf('.');
        if (idx == -1 || idx == filename.length() - 1) {
            return null;
        }
        return filename.substring(idx + 1);
    }

    public record StoredFileResult(
            String originalFilename,
            String storedFilename,
            String contentType,
            long sizeBytes,
            String relativePath
    ) { }
}
