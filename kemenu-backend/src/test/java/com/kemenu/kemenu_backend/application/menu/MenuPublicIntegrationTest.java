package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.helper.MenuRequestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuPublicIntegrationTest extends KemenuIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void anUnauthorizedUserCouldSeeAMenu() {
        customerRepository.create(randomCustomer);
        MenuRequest menuRequest = MenuRequestHelper.randomRequest(randomCustomer.getFirstBusiness().getId());

        UUID menuId = webTestClient
                .post().uri("/web/v1/menus")
                .body(Mono.just(menuRequest), MenuRequest.class)
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody();

        MenuResponse menuResponse = webTestClient
                .get().uri("/menus/" + menuId)
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(MenuResponse.class).returnResult().getResponseBody();

        assertEquals(menuRequest.getSections().get(0).getName(), menuResponse.getSections().get(0).getName());
    }
}