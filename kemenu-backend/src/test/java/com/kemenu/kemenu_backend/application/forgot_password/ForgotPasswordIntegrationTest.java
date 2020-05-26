package com.kemenu.kemenu_backend.application.forgot_password;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kemenu.kemenu_backend.application.email.EmailService;
import com.kemenu.kemenu_backend.application.security.Recaptcha;
import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.helper.customer.CustomerHelper;
import com.kemenu.kemenu_backend.helper.forgot_password.ForgotPasswordChangeRequestHelper;
import com.kemenu.kemenu_backend.helper.forgot_password.ForgotPasswordRequestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

class ForgotPasswordIntegrationTest extends KemenuIntegrationTest {

    @MockBean
    private Recaptcha recaptchaMock;

    @MockBean
    private EmailService emailService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void initAll() {
        Mockito.doNothing().when(emailService).sendMail(any(), anyString());
    }

    @Test
    void aCustomerCouldRequestForgotPassword() {
        Customer customer = CustomerHelper.withMenu();
        customerRepository.save(customer);
        ForgotPasswordRequest request = ForgotPasswordRequestHelper.random(customer.getEmail());
        Mockito.when(recaptchaMock.isValid(request.getRecaptchaToken())).thenReturn(true);

        String forgotPasswordId = webTestClient
                .post().uri("/public/forgot/password")
                .body(Mono.just(request), ForgotPasswordRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody().toString();

        verify(emailService, timeout(250).times(1)).sendMail(any(), anyString());
        assertTrue(forgotPasswordRepository.findById(forgotPasswordId).isPresent());
    }

    @Test
    void whenACustomerReceivesTheLinkOfTheForgotPasswordCouldChangeHisPassword() throws JsonProcessingException {
        Customer customer = CustomerHelper.withMenu();
        customerRepository.save(customer);
        String forgotPasswordId = forgotPasswordRepository.save(new ForgotPassword(customer.getEmail()));

        HttpHeaders headers = webTestClient
                .get().uri("/public/forgot/password/" + forgotPasswordId)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().exists("Set-Cookie")
                .expectBody().returnResult().getResponseHeaders();
        var forgotPasswordResponse = mapper.readValue(new String(Base64.getDecoder().decode(headers.get("Set-Cookie").get(0).replace("forgot_password_email=", ""))), ForgotPasswordResponse.class);

        assertEquals(customer.getEmail(), forgotPasswordResponse.getEmail());
        assertEquals(forgotPasswordId, forgotPasswordResponse.getId());
    }

    @Test
    void whenACustomerFillTheForgotPasswordChangeFormChangeHisPassword() {
        Customer customer = CustomerHelper.withMenu();
        String oldPassword = customer.getPassword();
        String customerId = customerRepository.save(customer);
        String forgotPasswordId = forgotPasswordRepository.save(new ForgotPassword(customer.getEmail()));
        var request = ForgotPasswordChangeRequestHelper.random();
        Mockito.when(recaptchaMock.isValid(request.getRecaptchaToken())).thenReturn(true);

        String customerIdResponse = webTestClient
                .patch().uri("/public/forgot/password/" + forgotPasswordId + "/email/" + customer.getEmail())
                .body(Mono.just(request), ForgotPasswordChangeRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody().toString();

        Customer updatedCustomer = customerRepository.findById(customerIdResponse).get();
        ForgotPassword forgotPassword = forgotPasswordRepository.findById(forgotPasswordId).get();

        assertEquals(customerId, customerIdResponse);
        assertNotEquals(oldPassword, updatedCustomer.getPassword());
        assertTrue(forgotPassword.isUsed());
    }

    @Test
    void aCustomerCannotChangeHisPasswordWithForgotPasswordIfThePasswordsDontMatch() {
        webTestClient
                .patch().uri("/public/forgot/password/id/email/test@example.com")
                .body(Mono.just(ForgotPasswordChangeRequestHelper.notSamePassword()), ForgotPasswordChangeRequest.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void aCustomerCannotChangeThePasswordWithRandomForgotPasswordIdAnotherCustomer() {
        ForgotPasswordChangeRequest request = ForgotPasswordChangeRequestHelper.random();
        Mockito.when(recaptchaMock.isValid(request.getRecaptchaToken())).thenReturn(true);

        webTestClient
                .patch().uri("/public/forgot/password/id/email/test@example.com")
                .body(Mono.just(request), ForgotPasswordChangeRequest.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void aCustomerCannotChangeThePasswordWithExpiredForgotPasswordRequest() {
        Customer customer = CustomerHelper.withMenu();
        String oldPassword = customer.getPassword();
        String customerId = customerRepository.save(customer);
        String forgotPasswordId = forgotPasswordRepository.save(new ForgotPassword(customer.getEmail(), Instant.now().minusSeconds(100)));
        var request = ForgotPasswordChangeRequestHelper.random();
        Mockito.when(recaptchaMock.isValid(request.getRecaptchaToken())).thenReturn(true);

        webTestClient
                .patch().uri("/public/forgot/password/" + forgotPasswordId + "/email/" + customer.getEmail())
                .body(Mono.just(request), ForgotPasswordChangeRequest.class)
                .exchange()
                .expectStatus().isNotFound();

        Customer notUpdatedCustomer = customerRepository.findById(customerId).get();
        ForgotPassword forgotPassword = forgotPasswordRepository.findById(forgotPasswordId).get();

        assertEquals(oldPassword, notUpdatedCustomer.getPassword());
        assertFalse(forgotPassword.isUsed());
        assertTrue(forgotPassword.isExpired());
    }
}