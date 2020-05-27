package com.kemenu.kemenu_backend.application.http;

import com.kemenu.kemenu_backend.infrastructure.cloudinary.CloudinaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/web/v1")
class UploadImageController {

    private final CloudinaryService cloudinaryService;

    @PostMapping("/upload/image")
    ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(cloudinaryService.upload(file));
    }
}
