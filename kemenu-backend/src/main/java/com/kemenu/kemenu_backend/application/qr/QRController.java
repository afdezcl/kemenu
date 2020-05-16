package com.kemenu.kemenu_backend.application.qr;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/web/v1")
class QRController {

    private final QRService qrService;

    @GetMapping("/qr/customers/{customerId}/businesses/{businessId}/menus/{menuId}")
    ResponseEntity<byte[]> readMenu(@PathVariable String customerId,
                                    @PathVariable String businessId,
                                    @PathVariable String menuId) {
        byte[] qrCode = qrService.generateQRCode(customerId, businessId, menuId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(qrCode, headers, HttpStatus.OK);
    }
}
