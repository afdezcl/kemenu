package com.kemenu.kemenu_backend.application.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kemenu.kemenu_backend.application.customer.CustomerRequest;
import com.kemenu.kemenu_backend.application.email.EmailService;
import com.kemenu.kemenu_backend.application.menu.CreateMenuRequest;
import com.kemenu.kemenu_backend.application.security.Recaptcha;
import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.ConfirmedEmail;
import com.kemenu.kemenu_backend.domain.model.ConfirmedEmailRepository;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.helper.LoginRequestHelper;
import com.kemenu.kemenu_backend.helper.customer.CustomerRequestHelper;
import com.kemenu.kemenu_backend.helper.menu.MenuRequestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

class LoginIntegrationTest extends KemenuIntegrationTest {

    @MockBean
    private Recaptcha recaptchaMock;

    @MockBean
    private EmailService emailService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ConfirmedEmailRepository confirmedEmailRepository;

    @Autowired
    private ObjectMapper mapper;

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @BeforeEach
    void initAll() {
        Mockito.doNothing().when(emailService).sendMail(any(), anyString());
    }

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

        Customer customer = customerRepository.findByEmail(customerRequest.getEmail().toUpperCase()).get();
        Optional<ConfirmedEmail> optionalConfirmedEmail;

        do {
            optionalConfirmedEmail = confirmedEmailRepository.findByEmail(customer.getEmail().toUpperCase());
        } while (optionalConfirmedEmail.isEmpty());

        ConfirmedEmail notConfirmedEmail = optionalConfirmedEmail.get();

        assertFalse(notConfirmedEmail.isConfirmed());
        verify(emailService, timeout(250).times(1)).sendMail(any(), anyString());

        webTestClient
                .get().uri("/public/confirm/email/" + notConfirmedEmail.getId())
                .exchange()
                .expectStatus().isOk();

        ConfirmedEmail confirmedEmail = confirmedEmailRepository.findByEmail(customer.getEmail()).get();
        assertTrue(confirmedEmail.isConfirmed());

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
                .post().uri("/web/v1/menus")
                .body(Mono.just(MenuRequestHelper.randomRequest(customer.firstBusiness().getId())), CreateMenuRequest.class)
                .header("Authorization", accessToken)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void aCustomerRequestSomeResourceWithoutAuthorizationHeader() {
        webTestClient
                .post().uri("/web/v1/menus")
                .body(Mono.just(MenuRequestHelper.randomRequest()), CreateMenuRequest.class)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void aCustomerWithExpiredTokenReceiveA401HTTPError() {
        webTestClient
                .post().uri("/web/v1/menus")
                .body(Mono.just(MenuRequestHelper.randomRequest()), CreateMenuRequest.class)
                .header("Authorization", generateExpiredAccessToken())
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void anAdminLoginThenCanUseProtectedResources() {
        JsonNode loginRequest = LoginRequestHelper.adminLogin(adminUsername, adminPassword, mapper);
        Mockito.when(recaptchaMock.isValid(loginRequest.get("recaptchaToken").asText())).thenReturn(true);

        HttpHeaders responseHeaders = webTestClient
                .post().uri("/login")
                .body(Mono.just(loginRequest), JsonNode.class)
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

    @Test
    void aCustomerWithUserRoleTryToUseAdminResourceAndGetAnUnauthorizedResponse() {
        webTestClient
                .get().uri("/admin/v1/customers")
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isForbidden();
    }
}
