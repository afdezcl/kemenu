package com.kemenu.kemenu_backend.application.http;

import com.kemenu.kemenu_backend.application.customer.CustomerRequest;
import com.kemenu.kemenu_backend.application.email.EmailService;
import com.kemenu.kemenu_backend.application.security.Recaptcha;
import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.ConfirmedEmail;
import com.kemenu.kemenu_backend.domain.model.ConfirmedEmailRepository;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.helper.CustomerRequestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class RegisterIntegrationTest extends KemenuIntegrationTest {

    @MockBean
    private Recaptcha recaptchaMock;

    @MockBean
    private EmailService emailService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ConfirmedEmailRepository confirmedEmailRepository;

    @BeforeEach
    void initAll() {
        Mockito.doNothing().when(emailService).sendMail(any(), anyString());
    }

    @Test
    void aCustomerCanRegisterInTheApplication() {
        CustomerRequest customerRequest = CustomerRequestHelper.randomRequest();
        Mockito.when(recaptchaMock.isValid(customerRequest.getRecaptchaToken())).thenReturn(true);

        UUID responseCustomerId = webTestClient
                .post().uri("/register")
                .body(Mono.just(customerRequest), CustomerRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody();
        Customer createdCustomer = customerRepository.findByEmail(customerRequest.getEmail()).get();
        ConfirmedEmail notConfirmedEmail = confirmedEmailRepository.findByEmail(createdCustomer.getEmail()).get();

        assertFalse(notConfirmedEmail.isConfirmed());
        verify(emailService, times(1)).sendMail(any(), anyString());

        webTestClient
                .get().uri("/public/confirm/email/" + notConfirmedEmail.getId())
                .exchange()
                .expectStatus().isOk();

        ConfirmedEmail confirmedEmail = confirmedEmailRepository.findByEmail(createdCustomer.getEmail()).get();
        assertTrue(confirmedEmail.isConfirmed());

        assertEquals(responseCustomerId.toString(), createdCustomer.getId());
    }

    @Test
    void aCustomerCannotRegisterIfRecaptchaIsNotValid() {
        CustomerRequest customerRequest = CustomerRequestHelper.randomRequest();
        Mockito.when(recaptchaMock.isValid(customerRequest.getRecaptchaToken())).thenReturn(false);

        webTestClient
                .post().uri("/register")
                .body(Mono.just(customerRequest), CustomerRequest.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.TOO_MANY_REQUESTS);

        verify(emailService, times(0)).sendMail(any(), anyString());
    }

    @Test
    void aCustomerCannotRegisterIfTheEmailAlreadyExists() {
        CustomerRequest customerRequest = CustomerRequestHelper.randomRequest();
        Mockito.when(recaptchaMock.isValid(customerRequest.getRecaptchaToken())).thenReturn(true);

        webTestClient
                .post().uri("/register")
                .body(Mono.just(customerRequest), CustomerRequest.class)
                .exchange()
                .expectStatus().isOk();

        webTestClient
                .post().uri("/register")
                .body(Mono.just(customerRequest), CustomerRequest.class)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);

        verify(emailService, times(1)).sendMail(any(), anyString());
    }

    @Test
    void aCustomerCannotRegisterIfTheEmailIsWrong() {
        CustomerRequest customerRequest = CustomerRequestHelper.withWrongEmail();
        Mockito.when(recaptchaMock.isValid(customerRequest.getRecaptchaToken())).thenReturn(true);

        webTestClient
                .post().uri("/register")
                .body(Mono.just(customerRequest), CustomerRequest.class)
                .exchange()
                .expectStatus().isBadRequest();

        verify(emailService, times(0)).sendMail(any(), anyString());
    }
}