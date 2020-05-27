package com.kemenu.kemenu_backend.infrastructure.cloudinary;

import com.cloudinary.Cloudinary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(@Value("${app.cloudinary.key}") String key,
                             @Value("${app.cloudinary.secret}") String secret,
                             @Value("${app.cloudinary.cloudname}") String cloudName) {
        this.cloudinary = new Cloudinary(
                Map.of(
                        "api_key", key,
                        "api_secret", secret,
                        "cloud_name", cloudName
                )
        );
    }

    public String upload(MultipartFile file) {
        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of("resource_type", "auto"));
            CloudinaryUploadResponse uploadResponse = CloudinaryUploadResponse.from(uploadResult);
            return uploadResponse.getSecureUrl();
        } catch (IOException e) {
            log.error("Failure while uploading photo to Cloudinary", e);
            return "";
        }
    }
}
