package com.kemenu.kemenu_backend.infrastructure.cloudinary;

import com.cloudinary.Cloudinary;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @SneakyThrows
    public boolean upload(MultipartFile file) {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of());
        return false;
    }
}
