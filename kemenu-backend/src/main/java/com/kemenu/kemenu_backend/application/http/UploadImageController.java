package com.kemenu.kemenu_backend.application.http;

import com.kemenu.kemenu_backend.application.security.IntrospectiveService;
import com.kemenu.kemenu_backend.infrastructure.cloudinary.CloudinaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/web/v1")
class UploadImageController {

    private final IntrospectiveService introspectiveService;
    private final CloudinaryService cloudinaryService;

    @PostMapping("/customer/{email}/upload/image")
    ResponseEntity<UploadImageResponse> uploadImage(@RequestHeader(value = "Authorization") String token,
                                                    @PathVariable String email,
                                                    @RequestParam("file") MultipartFile file) {
        return introspectiveService
                .doCallOnMe(
                        token,
                        email,
                        () -> ResponseEntity.ok(new UploadImageResponse(cloudinaryService.upload(file)))
                );
    }

    @PostMapping("/customer/{email}/upload/image/resized")
    ResponseEntity<UploadImageResponse> uploadImageResized(@RequestHeader(value = "Authorization") String token,
                                                           @PathVariable String email,
                                                           @RequestParam("file") MultipartFile file) {
        return introspectiveService
                .doCallOnMe(
                        token,
                        email,
                        () -> ResponseEntity.ok(new UploadImageResponse(cloudinaryService.uploadResized(file)))
                );
    }
}
