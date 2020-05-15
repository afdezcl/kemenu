package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.helper.MenuRequestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MenuWebIntegrationTest extends KemenuIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void aCustomerCouldCreateAMenu() {
        customerRepository.save(randomCustomer);

        UUID menuId = webTestClient
                .post().uri("/web/v1/menus")
                .body(Mono.just(MenuRequestHelper.randomRequest(randomCustomer.getFirstBusiness().getId())), CreateMenuRequest.class)
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody();

        Menu createdMenu = customerRepository.findById(randomCustomer.getId()).get().getFirstMenu();

        assertEquals(menuId.toString(), createdMenu.getId());
    }

    @Test
    void aCustomerCouldChangeAMenu() {
        customerRepository.save(randomCustomer);

        UUID menuId = webTestClient
                .post().uri("/web/v1/menus")
                .body(Mono.just(MenuRequestHelper.randomRequest(randomCustomer.getFirstBusiness().getId())), CreateMenuRequest.class)
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody();

        Customer customerWithCreatedMenu = customerRepository.findById(randomCustomer.getId()).get();

        webTestClient
                .put().uri("/web/v1/menus")
                .body(Mono.just(MenuRequestHelper.updateMenuRequest(randomCustomer.getFirstBusiness().getId(), menuId.toString())), UpdateMenuRequest.class)
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody();

        Customer customerWithUpdatedMenu = customerRepository.findById(randomCustomer.getId()).get();

        assertEquals(customerWithCreatedMenu.getFirstMenu().getId(), customerWithUpdatedMenu.getFirstMenu().getId());
        assertNotEquals(customerWithCreatedMenu.getFirstMenu().getFirstSection().getName(), customerWithUpdatedMenu.getFirstMenu().getFirstSection().getName());
    }
}