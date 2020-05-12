package com.kemenu.kemenu_backend.application.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kemenu.kemenu_backend.application.customer.CustomerRequest;
import com.kemenu.kemenu_backend.application.security.Recaptcha;
import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.helper.CustomerRequestHelper;
import com.kemenu.kemenu_backend.helper.LoginRequestHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.UUID;

class LoginIntegrationTest extends KemenuIntegrationTest {

    @MockBean
    private Recaptcha recaptchaMock;

    @Autowired
    private ObjectMapper mapper;

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Test
    void aCustomerRegisterAnAccountAndLoginThenCanUseProtectedResources() {
        CustomerRequest customerRequest = CustomerRequestHelper.randomRequest();
        Mockito.when(recaptchaMock.isValid(customerRequest.getRecaptchaToken())).thenReturn(true);

        webTestClient
                .post().uri("/register")
                .body(Mono.just(customerRequest), CustomerRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class);

        HttpHeaders responseHeaders = webTestClient
                .post().uri("/login")
                .body(Mono.just(LoginRequestHelper.createLoginRequest(customerRequest, mapper)), JsonNode.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().exists("Authorization")
                .expectHeader().exists("JWT-Refresh-Token")
                .expectBody().returnResult().getResponseHeaders();

        String accessToken = responseHeaders.get("Authorization").get(0);

        webTestClient
                .get().uri("/web/v1/customers")
                .header("Authorization", accessToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].email").isEqualTo(customerRequest.getEmail());
    }

    @Test
    void anAdminLoginThenCanUseProtectedResources() {
        Mockito.when(recaptchaMock.isValid("admin")).thenReturn(true);

        HttpHeaders responseHeaders = webTestClient
                .post().uri("/login")
                .body(Mono.just(LoginRequestHelper.adminLogin(adminUsername, adminPassword, mapper)), JsonNode.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().exists("Authorization")
                .expectHeader().exists("JWT-Refresh-Token")
                .expectBody().returnResult().getResponseHeaders();

        String accessToken = responseHeaders.get("Authorization").get(0);

        webTestClient
                .get().uri("/admin/v1/customers")
                .header("Authorization", accessToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].email").isEqualTo(adminUsername);
    }
}