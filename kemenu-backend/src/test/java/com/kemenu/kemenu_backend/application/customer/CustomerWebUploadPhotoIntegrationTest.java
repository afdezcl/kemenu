package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.Business;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.helper.customer.CustomerHelper;
import com.kemenu.kemenu_backend.helper.customer.CustomerRequestHelper;
import com.kemenu.kemenu_backend.infrastructure.cloudinary.CloudinaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;

class CustomerWebUploadPhotoIntegrationTest extends KemenuIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @MockBean
    private CloudinaryService cloudinaryService;

    @BeforeEach
    void initAll() {
        Mockito.when(cloudinaryService.upload(any())).thenReturn(UUID.randomUUID().toString());
    }

    @Test
    void aCustomerCouldUploadABusinessPhoto() {
        Customer customer = CustomerHelper.withMenu();
        Business business = customer.firstBusiness();
        customerRepository.save(customer);

        String customerId = webTestClient
                .post().uri("/web/v1/customer/" + customer.getEmail() + "/business/" + business.getId() + "/upload/photo")
                .body(BodyInserters.fromMultipartData(CustomerRequestHelper.photo().build()))
                .header("Authorization", generateAccessToken(customer.getEmail()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody().toString();

        Customer updatedCustomer = customerRepository.findById(customerId).get();
        Business updatedBusiness = updatedCustomer.firstBusiness();

        assertEquals(customer.getId(), updatedCustomer.getId());
        assertFalse(updatedBusiness.getImageUrl().isEmpty());
    }
}
