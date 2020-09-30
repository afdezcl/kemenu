package com.kemenu.kemenu_backend.application.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class Recaptcha {

    private final ObjectMapper mapper;
    private final WebClient webClient;

    @Value("${app.recaptcha.secret}")
    private String recaptchaSecret;

    @SneakyThrows
    public boolean isValid(String recaptchaToken) {
        JsonNode responseRecaptcha = mapper.readTree(verifyRecaptcha(recaptchaToken));
        boolean success = responseRecaptcha.get("success").asBoolean();

        if (!success) {
            log.info("Recaptcha does not success");
            return false;
        }

        BigDecimal score = new BigDecimal(responseRecaptcha.get("score").asText());
        String action = responseRecaptcha.get("action").asText();

        boolean isValid = score.compareTo(new BigDecimal("0.3")) >= 0 && action.equals("login");

        if (!isValid) {
            log.info("Invalid Recaptcha with score {} and action {}", score.toPlainString(), action);
        }

        return isValid;
    }

    @SneakyThrows
    private String verifyRecaptcha(String recaptchaToken) {
        return webClient.post()
                .uri("https://www.google.com/recaptcha/api/siteverify")
                .body(BodyInserters.fromMultipartData("secret", recaptchaSecret).with("response", recaptchaToken))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
