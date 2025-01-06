package com.moodbeat.analysis.uploadservice.controllers;


import com.moodbeat.analysis.uploadservice.models.FileUpload;
import com.moodbeat.analysis.uploadservice.services.UploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
public class UploadController {
    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/audio")
    public ResponseEntity<String> uploadAudio(@RequestParam("file")MultipartFile file) throws IOException {
        FileUpload fileUpload = new FileUpload(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes(),
                file.getSize()
        );

        uploadService.processUpload(fileUpload);

        return ResponseEntity.ok("File uploaded successfully");
    }
}
