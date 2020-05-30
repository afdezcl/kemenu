package com.kemenu.kemenu_backend.application.http;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = UploadImageResponse.UploadImageResponseBuilder.class)
public class UploadImageResponse {
    String url;
}
