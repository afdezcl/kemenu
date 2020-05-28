package com.kemenu.kemenu_backend.application.http;

import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.helper.customer.CustomerHelper;
import com.kemenu.kemenu_backend.helper.customer.CustomerRequestHelper;
import com.kemenu.kemenu_backend.infrastructure.cloudinary.CloudinaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;

class UploadImageIntegrationTest extends KemenuIntegrationTest {

    @MockBean
    private CloudinaryService cloudinaryService;

    @BeforeEach
    void initAll() {
        Mockito.when(cloudinaryService.upload(any())).thenReturn(UUID.randomUUID().toString());
    }

    @Test
    void aCustomerCouldUploadAnImage() {
        Customer customer = CustomerHelper.withMenu();

        UploadImageResponse response = webTestClient
                .post().uri("/web/v1/customer/" + customer.getEmail() + "/upload/image")
                .body(BodyInserters.fromMultipartData(CustomerRequestHelper.photo().build()))
                .header("Authorization", generateAccessToken(customer.getEmail()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UploadImageResponse.class).returnResult().getResponseBody();

        assertFalse(response.getUrl().isEmpty());
    }
}
