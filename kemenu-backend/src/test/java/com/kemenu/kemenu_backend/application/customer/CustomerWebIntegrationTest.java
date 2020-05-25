package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.application.business.BusinessChangeNameRequest;
import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.Business;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.helper.business.BusinessChangeNameRequestHelper;
import com.kemenu.kemenu_backend.helper.customer.CustomerHelper;
import com.kemenu.kemenu_backend.helper.customer.PasswordChangeRequestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CustomerWebIntegrationTest extends KemenuIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void aCustomerCanFindHimself() {
        Customer customer = CustomerHelper.withMenu();
        customerRepository.save(customer);

        CustomerResponse customerResponse = webTestClient
                .get().uri("/web/v1/customer/" + customer.getEmail())
                .header("Authorization", generateAccessToken(customer.getEmail()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerResponse.class).returnResult().getResponseBody();

        assertEquals(customer.firstBusiness().getName(), customerResponse.getBusinesses().get(0).getName());
    }

    @Test
    void aCustomerCantFindInformationAboutOtherCustomer() {
        webTestClient
                .get().uri("/web/v1/customer/other@customer.com")
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void aCustomerCouldChangeHisPassword() {
        Customer customer = CustomerHelper.withMenu();
        String oldPassword = customer.getPassword();
        String customerId = customerRepository.save(customer);
        PasswordChangeRequest passwordChangeRequest = PasswordChangeRequestHelper.random();

        String customerIdResponse = webTestClient
                .patch().uri("/web/v1/customer/" + customer.getEmail() + "/password/change")
                .body(Mono.just(passwordChangeRequest), PasswordChangeRequest.class)
                .header("Authorization", generateAccessToken(customer.getEmail()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody().toString();

        Customer updatedCustomer = customerRepository.findById(customerIdResponse).get();

        assertEquals(customerId, customerIdResponse);
        assertNotEquals(oldPassword, updatedCustomer.getPassword());
    }

    @Test
    void aCustomerCannotChangeHisPasswordIfThePasswordsDontMatch() {
        webTestClient
                .patch().uri("/web/v1/customer/test@example.com/password/change")
                .body(Mono.just(PasswordChangeRequestHelper.notSamePassword()), PasswordChangeRequest.class)
                .header("Authorization", generateAccessToken("test@example.com"))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void aCustomerCouldChangeTheBusinessName() {
        Customer customer = CustomerHelper.withMenu();
        Business business = customer.firstBusiness();
        String customerId = customerRepository.save(customer);
        var request = BusinessChangeNameRequestHelper.random();

        String customerIdResponse = webTestClient
                .patch().uri("/web/v1/customer/" + customer.getEmail() + "/business/" + business.getId())
                .body(Mono.just(request), BusinessChangeNameRequest.class)
                .header("Authorization", generateAccessToken(customer.getEmail()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody().toString();

        Customer updatedCustomer = customerRepository.findById(customerIdResponse).get();

        assertEquals(customerId, customerIdResponse);
        assertNotEquals(business.getName(), updatedCustomer.firstBusiness().getName());
    }

    @Test
    void aCustomerCouldNotChangeTheBusinessNameOfUnkown() {
        Customer customer = CustomerHelper.withMenu();
        customerRepository.save(customer);
        var request = BusinessChangeNameRequestHelper.random();

        webTestClient
                .patch().uri("/web/v1/customer/" + customer.getEmail() + "/business/" + UUID.randomUUID().toString())
                .body(Mono.just(request), BusinessChangeNameRequest.class)
                .header("Authorization", generateAccessToken(customer.getEmail()))
                .exchange()
                .expectStatus().isNotFound();
    }
}