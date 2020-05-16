package com.kemenu.kemenu_backend.application.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QRService {

    @Value("${app.cors}")
    private List<String> allowedOrigins;

    @SneakyThrows
    public byte[] generateQRCode(String customerId, String businessId, String menuId) {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(generateShowMenuUrl(customerId, businessId, menuId), BarcodeFormat.QR_CODE, 350, 350);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", stream);
        return stream.toByteArray();
    }

    private String generateShowMenuUrl(String customerId, String businessId, String menuId) {
        return allowedOrigins.get(0)
                + "/customers/" + customerId
                + "/businesses/" + businessId
                + "/menus/" + menuId;
    }
}
