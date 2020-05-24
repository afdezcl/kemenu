package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.domain.model.ShortUrl;
import com.kemenu.kemenu_backend.domain.model.ShortUrlRepository;
import com.kemenu.kemenu_backend.helper.menu.MenuRequestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuWebIntegrationTest extends KemenuIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Test
    void aCustomerCouldCreateAMenu() {
        customerRepository.save(randomCustomer);
        String businessId = randomCustomer.firstBusiness().getId();

        CreateMenuResponse createMenuResponse = webTestClient
                .post().uri("/web/v1/menus")
                .body(Mono.just(MenuRequestHelper.randomRequest(businessId)), CreateMenuRequest.class)
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(CreateMenuResponse.class).returnResult().getResponseBody();

        Customer customer = customerRepository.findById(randomCustomer.getId()).get();
        ShortUrl shortUrl = shortUrlRepository.findById(createMenuResponse.getShortUrlId()).get();

        assertTrue(customer.findMenu(businessId, shortUrl.getMenus().get(0)).isPresent());
    }

    @Test
    void aCustomerCouldChangeAMenu() {
        customerRepository.save(randomCustomer);
        String businessId = randomCustomer.firstBusiness().getId();

        CreateMenuResponse createMenuResponse = webTestClient
                .post().uri("/web/v1/menus")
                .body(Mono.just(MenuRequestHelper.randomRequest(businessId)), CreateMenuRequest.class)
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(CreateMenuResponse.class).returnResult().getResponseBody();

        Customer customerWithCreatedMenu = customerRepository.findById(randomCustomer.getId()).get();
        ShortUrl createdShortUrl = shortUrlRepository.findById(createMenuResponse.getShortUrlId()).get();
        Menu createdMenu = customerWithCreatedMenu.findMenu(businessId, createdShortUrl.getMenus().get(0)).get();

        UUID updatedMenuId = webTestClient
                .put().uri("/web/v1/menus")
                .body(Mono.just(MenuRequestHelper.updateMenuRequest(businessId, createdShortUrl.getMenus().get(0))), UpdateMenuRequest.class)
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody();

        Customer customerWithUpdatedMenu = customerRepository.findById(randomCustomer.getId()).get();
        Menu updatedMenu = customerWithUpdatedMenu.findMenu(businessId, updatedMenuId.toString()).get();

        assertEquals(createdMenu.getId(), updatedMenu.getId());
        assertNotEquals(createdMenu.getSections().get(0).getName(), updatedMenu.getSections().get(0).getName());
    }
}