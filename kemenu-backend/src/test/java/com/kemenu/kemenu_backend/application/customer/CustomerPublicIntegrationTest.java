package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.application.menu.CreateMenuRequest;
import com.kemenu.kemenu_backend.application.menu.MenuResponse;
import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.helper.MenuRequestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerPublicIntegrationTest extends KemenuIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void anUnauthorizedUserCouldSeeAMenu() {
        customerRepository.save(randomCustomer);
        String businessId = randomCustomer.getFirstBusiness().getId();
        CreateMenuRequest createMenuRequest = MenuRequestHelper.randomRequest(businessId);

        UUID menuId = webTestClient
                .post().uri("/web/v1/menus")
                .body(Mono.just(createMenuRequest), CreateMenuRequest.class)
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody();

        MenuResponse menuResponse = webTestClient
                .get().uri("/customers/" + randomCustomer.getId() + "/businesses/" + businessId + "/menus/" + menuId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MenuResponse.class).returnResult().getResponseBody();

        assertEquals(createMenuRequest.getSections().get(0).getName(), menuResponse.getSections().get(0).getName());
    }
}