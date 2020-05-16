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
        String businessId = randomCustomer.firstBusiness().getId();

        UUID menuId = webTestClient
                .post().uri("/web/v1/menus")
                .body(Mono.just(MenuRequestHelper.randomRequest(businessId)), CreateMenuRequest.class)
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody();

        Customer customer = customerRepository.findById(randomCustomer.getId()).get();

        assertEquals(menuId.toString(), customer.findMenu(businessId, menuId.toString()).get().getId());
    }

    @Test
    void aCustomerCouldChangeAMenu() {
        customerRepository.save(randomCustomer);
        String businessId = randomCustomer.firstBusiness().getId();

        UUID createdMenuId = webTestClient
                .post().uri("/web/v1/menus")
                .body(Mono.just(MenuRequestHelper.randomRequest(businessId)), CreateMenuRequest.class)
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody();

        Customer customerWithCreatedMenu = customerRepository.findById(randomCustomer.getId()).get();
        Menu createdMenu = customerWithCreatedMenu.findMenu(businessId, createdMenuId.toString()).get();

        UUID updatedMenuId = webTestClient
                .put().uri("/web/v1/menus")
                .body(Mono.just(MenuRequestHelper.updateMenuRequest(businessId, createdMenuId.toString())), UpdateMenuRequest.class)
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody();

        Customer customerWithUpdatedMenu = customerRepository.findById(randomCustomer.getId()).get();
        Menu updatedMenu = customerWithUpdatedMenu.findMenu(businessId, updatedMenuId.toString()).get();

        assertEquals(createdMenu.getId(), updatedMenu.getId());
        assertNotEquals(createdMenu, updatedMenu);
    }
}