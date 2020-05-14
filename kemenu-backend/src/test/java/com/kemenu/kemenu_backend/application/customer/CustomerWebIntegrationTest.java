package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.helper.CustomerHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerWebIntegrationTest extends KemenuIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void aCustomerCanFindHimself() {
        Customer customer = CustomerHelper.withMenu();
        customerRepository.create(customer);

        CustomerResponse customerResponse = webTestClient
                .get().uri("/web/v1/customer/" + customer.getEmail())
                .header("Authorization", generateAccessToken(customer.getEmail()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerResponse.class).returnResult().getResponseBody();

        assertEquals(customer.getFirstBusiness().getName(), customerResponse.getBusinesses().get(0).getName());
    }

    @Test
    void aCustomerCantFindInformationAboutOtherCustomer() {
        webTestClient
                .get().uri("/web/v1/customer/other@customer.com")
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isForbidden();
    }
}