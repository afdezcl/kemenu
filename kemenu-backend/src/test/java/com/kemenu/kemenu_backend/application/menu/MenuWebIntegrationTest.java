package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.domain.model.ShortUrl;
import com.kemenu.kemenu_backend.domain.model.ShortUrlRepository;
import com.kemenu.kemenu_backend.helper.MenuRequestHelper;
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

        UUID shortUrlId = webTestClient
                .post().uri("/web/v1/menus")
                .body(Mono.just(MenuRequestHelper.randomRequest(businessId)), CreateMenuRequest.class)
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody();

        Customer customer = customerRepository.findById(randomCustomer.getId()).get();
        ShortUrl shortUrl = shortUrlRepository.findById(shortUrlId.toString()).get();

        assertTrue(customer.findMenu(businessId, shortUrl.getMenuId()).isPresent());
    }

    @Test
    void aCustomerCouldChangeAMenu() {
        customerRepository.save(randomCustomer);
        String businessId = randomCustomer.firstBusiness().getId();

        UUID createdShortUrlId = webTestClient
                .post().uri("/web/v1/menus")
                .body(Mono.just(MenuRequestHelper.randomRequest(businessId)), CreateMenuRequest.class)
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody();

        Customer customerWithCreatedMenu = customerRepository.findById(randomCustomer.getId()).get();
        ShortUrl createdShortUrl = shortUrlRepository.findById(createdShortUrlId.toString()).get();
        Menu createdMenu = customerWithCreatedMenu.findMenu(businessId, createdShortUrl.getMenuId()).get();

        UUID updatedShortUrlId = webTestClient
                .put().uri("/web/v1/menus")
                .body(Mono.just(MenuRequestHelper.updateMenuRequest(businessId, createdShortUrlId.toString())), UpdateMenuRequest.class)
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody();

        Customer customerWithUpdatedMenu = customerRepository.findById(randomCustomer.getId()).get();
        ShortUrl updatedShortUrl = shortUrlRepository.findById(updatedShortUrlId.toString()).get();
        Menu updatedMenu = customerWithUpdatedMenu.findMenu(businessId, updatedShortUrl.getMenuId()).get();

        assertEquals(createdMenu.getId(), updatedMenu.getId());
        assertNotEquals(createdMenu, updatedMenu);
    }
}