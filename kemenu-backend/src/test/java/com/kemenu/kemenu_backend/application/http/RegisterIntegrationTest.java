package com.kemenu.kemenu_backend.application.http;

import com.kemenu.kemenu_backend.application.customer.CustomerRequest;
import com.kemenu.kemenu_backend.application.security.Recaptcha;
import com.kemenu.kemenu_backend.common.MongoTest;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.helper.CustomerRequestHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles(profiles = "test")
class RegisterIntegrationTest extends MongoTest {

    @MockBean
    private Recaptcha recaptchaMock;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WebTestClient webTestClient;

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
    }
}