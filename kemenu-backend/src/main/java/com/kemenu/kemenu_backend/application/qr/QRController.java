package com.kemenu.kemenu_backend.application.qr;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@AllArgsConstructor
@RequestMapping("/web/v1")
class QRController {

    private final QRService qrService;

    @GetMapping("/qr/{shortUrlId}")
    ResponseEntity<QRResponse> readMenu(@PathVariable String shortUrlId) {
        byte[] qrCode = qrService.generateQRCode(shortUrlId);
        String base64QRCode = Base64.getEncoder().encodeToString(qrCode);
        return ResponseEntity.ok(QRResponse.builder().qr(base64QRCode).build());
    }
}
