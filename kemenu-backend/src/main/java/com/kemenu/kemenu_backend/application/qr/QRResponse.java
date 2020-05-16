package com.kemenu.kemenu_backend.application.qr;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = QRResponse.QRResponseBuilder.class)
public class QRResponse {
    String qr;
}
