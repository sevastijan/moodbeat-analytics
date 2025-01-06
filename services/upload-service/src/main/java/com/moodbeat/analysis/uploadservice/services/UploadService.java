package com.moodbeat.analysis.uploadservice.services;

import com.moodbeat.analysis.uploadservice.exceptions.InvalidFileTypeException;
import com.moodbeat.analysis.uploadservice.models.FileUpload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UploadService {
    private final S3StorageService s3StorageService;

    public UploadService(S3StorageService s3StorageService) {
        this.s3StorageService = s3StorageService;
    }

    public void processUpload(FileUpload fileUpload) {
        log.info("Processing file upload: {}", fileUpload);

        validateAudioFile(fileUpload);
        saveFile(fileUpload);
        sendToAnalysisService(fileUpload);
    }

    private void validateAudioFile(FileUpload fileUpload) {
        if(!isAudioFile(fileUpload.contentType())) {
            throw new InvalidFileTypeException("File is not an audio file");
        }
    }

    private boolean isAudioFile(String contentType) {
        return contentType != null && (
                contentType.startsWith("audio/") || contentType.equals("application/octet-stream")
        );
    }

    private void saveFile(FileUpload file) {
        s3StorageService.store(file);
    }

    private void sendToAnalysisService(FileUpload file) {
        // TODO: Implement analysis service communication
    }
}
