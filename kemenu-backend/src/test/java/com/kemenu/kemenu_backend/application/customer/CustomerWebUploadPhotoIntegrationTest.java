package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.helper.customer.CustomerHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerWebUploadPhotoIntegrationTest extends KemenuIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void aCustomerCouldUploadABusinessPhoto() {
        Customer customer = CustomerHelper.withMenu();
        customerRepository.save(customer);
        MockMultipartFile multipartFile = new MockMultipartFile()

        CustomerResponse customerResponse = webTestClient
                .get().uri("/web/v1/customer/" + customer.getEmail())
                .header("Authorization", generateAccessToken(customer.getEmail()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerResponse.class).returnResult().getResponseBody();

        assertEquals(customer.firstBusiness().getName(), customerResponse.getBusinesses().get(0).getName());
    }
}
