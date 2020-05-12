package com.kemenu.kemenu_backend.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kemenu.kemenu_backend.application.customer.CustomerRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequestHelper {

    public static JsonNode createLoginRequest(CustomerRequest customerRequest, ObjectMapper mapper) {
        ObjectNode loginRequest = mapper.createObjectNode();
        loginRequest.put("email", customerRequest.getEmail());
        loginRequest.put("password", customerRequest.getPassword());
        loginRequest.put("recaptchaToken", customerRequest.getRecaptchaToken());
        return loginRequest;
    }
}
