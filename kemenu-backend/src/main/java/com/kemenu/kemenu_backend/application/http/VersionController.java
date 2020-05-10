package com.kemenu.kemenu_backend.application.http;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    @Value("${app.version}")
    private String appVersion;

    @GetMapping("/app/version")
    public VersionResponse getVersion() {
        return new VersionResponse(appVersion.replace("-SNAPSHOT", ""));
    }

    @lombok.Value
    @Builder(toBuilder = true)
    @JsonDeserialize(builder = VersionResponse.VersionResponseBuilder.class)
    private static class VersionResponse {
        String version;
    }
}
