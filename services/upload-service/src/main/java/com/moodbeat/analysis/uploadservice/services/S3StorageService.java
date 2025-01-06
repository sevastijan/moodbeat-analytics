package com.moodbeat.analysis.uploadservice.services;

import com.moodbeat.analysis.uploadservice.exceptions.StorageException;
import com.moodbeat.analysis.uploadservice.models.FileUpload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.UUID;

@Service
@Slf4j
public class S3StorageService {
    private final S3Client s3Client;
    private final String bucketName;

    public S3StorageService(@Value("${aws.s3.bucket}") String bucketName) {
        this.s3Client = S3Client.builder()
                .region(Region.EU_CENTRAL_1)
                .build();

        this.bucketName = bucketName;
    }

    public String store(FileUpload file) {
        String key = generateUniqueKey(file.filename());
        try {
            s3Client.putObject(builder -> builder
                    .bucket(bucketName)
                    .key(key)
                    .build(),
                    RequestBody.fromBytes(file.content()));

            return key;
        } catch (S3Exception e) {
            throw new StorageException("Failed to store file in S3", e);
        }
    }

    private String generateUniqueKey(String filename) {
        return UUID.randomUUID() + "_" + filename;
    }
}
