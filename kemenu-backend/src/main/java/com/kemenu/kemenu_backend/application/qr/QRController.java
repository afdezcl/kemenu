package com.kemenu.kemenu_backend.application.qr;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@AllArgsConstructor
@RequestMapping("/web/v1")
public class QRController {

    private final QRService qrService;

    @GetMapping("/qr/customers/{customerId}/businesses/{businessId}/menus/{menuId}")
    ResponseEntity<String> readMenu(@PathVariable String customerId,
                                    @PathVariable String businessId,
                                    @PathVariable String menuId,
                                    HttpServletResponse response) {
        byte[] qrCode = qrService.generateQRCode(customerId, businessId, menuId);

        try (InputStream inputStream = new ByteArrayInputStream(qrCode)) {
            StreamUtils.copy(inputStream, response.getOutputStream());
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
