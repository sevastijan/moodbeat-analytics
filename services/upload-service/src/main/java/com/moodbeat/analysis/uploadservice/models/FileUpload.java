package com.moodbeat.analysis.uploadservice.models;

public record FileUpload(
        String filename,
        String contentType,
        byte[] content,
        long size
) {
}
