package com.kemenu.kemenu_backend.application.http;

import com.kemenu.kemenu_backend.application.security.JWTService;
import com.kemenu.kemenu_backend.infrastructure.cloudinary.CloudinaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    private final JWTService jwtService;
    private final CloudinaryService cloudinaryService;

    @PostMapping("/customer/{email}/upload/image")
    ResponseEntity<UploadImageResponse> uploadImage(@RequestHeader(value = "Authorization") String token,
                                                    @PathVariable String email,
                                                    @RequestParam("file") MultipartFile file) {
        String tokenEmail = jwtService.decodeAccessToken(token).getSubject();

        if (!email.equals(tokenEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(new UploadImageResponse(cloudinaryService.upload(file)));
    }
}
