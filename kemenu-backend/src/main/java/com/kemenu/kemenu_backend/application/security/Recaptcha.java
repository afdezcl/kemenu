package com.kemenu.kemenu_backend.application.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class Recaptcha {

    private final ObjectMapper mapper;
    private final OkHttpClient httpClient;

    @Value("${app.recaptcha.secret}")
    private String recaptchaSecret;

    @SneakyThrows
    public boolean isValid(String recaptchaToken) {
        JsonNode responseRecaptcha = mapper.readTree(verifyRecaptcha(recaptchaToken));
        boolean success = responseRecaptcha.get("success").asBoolean();

        if (!success) {
            return false;
        }

        BigDecimal score = new BigDecimal(responseRecaptcha.get("score").asText());
        String action = responseRecaptcha.get("action").asText();

        return score.compareTo(new BigDecimal("0.8")) >= 0 && action.equals("login");
    }

    @SneakyThrows
    private String verifyRecaptcha(String recaptchaToken) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("secret", recaptchaSecret)
                .addFormDataPart("response", recaptchaToken)
                .build();

        Request request = new Request.Builder()
                .url("https://www.google.com/recaptcha/api/siteverify")
                .post(requestBody)
                .build();

        return httpClient.newCall(request).execute().body().string();
    }
}
