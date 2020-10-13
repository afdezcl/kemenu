package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.domain.model.ShortUrl;
import com.kemenu.kemenu_backend.domain.model.ShortUrlRepository;
import com.kemenu.kemenu_backend.helper.menu.MenuRequestHelper;
import com.kemenu.kemenu_backend.helper.menu.MenuWebClient;
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

        CreateMenuResponse createMenuResponse = MenuWebClient.create(webTestClient, businessId, generateAccessToken());

        Customer customer = customerRepository.findById(randomCustomer.getId()).get();
        ShortUrl shortUrl = shortUrlRepository.findById(createMenuResponse.getShortUrlId()).get();

        assertTrue(customer.findMenu(businessId, shortUrl.getMenus().get(0)).isPresent());
    }

    @Test
    void aCustomerCouldChangeAMenu() {
        customerRepository.save(randomCustomer);
        String businessId = randomCustomer.firstBusiness().getId();

        CreateMenuResponse createMenuResponse = MenuWebClient.create(webTestClient, businessId, generateAccessToken());

        Customer customerWithCreatedMenu = customerRepository.findById(randomCustomer.getId()).get();
        ShortUrl createdShortUrl = shortUrlRepository.findById(createMenuResponse.getShortUrlId()).get();
        Menu createdMenu = customerWithCreatedMenu.findMenu(businessId, createdShortUrl.getMenus().get(0)).get();

        UpdateMenuResponse updatedMenu = webTestClient
                .put().uri("/web/v1/menus")
                .body(Mono.just(MenuRequestHelper.updateMenuRequest(businessId, createdShortUrl.getMenus().get(0))), UpdateMenuRequest.class)
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UpdateMenuResponse.class).returnResult().getResponseBody();

        Customer customerWithUpdatedMenu = customerRepository.findById(randomCustomer.getId()).get();
        Menu storedUpdatedMenu = customerWithUpdatedMenu.findMenu(businessId, updatedMenu.getId()).get();

        assertEquals(createdMenu.getId(), storedUpdatedMenu.getId());
        assertNotEquals(createdMenu.getSections().get(0).getName(), storedUpdatedMenu.getSections().get(0).getName());
    }

    @Test
    void aCustomerCouldCreateMoreThanOneMenu() {
        customerRepository.save(randomCustomer);
        String businessId = randomCustomer.firstBusiness().getId();

        CreateMenuResponse createMenuResponse = MenuWebClient.create(webTestClient, businessId, generateAccessToken());
        CreateMenuResponse createMenuResponse2 = MenuWebClient.create(webTestClient, businessId, generateAccessToken());
        CreateMenuResponse createMenuResponse3 = MenuWebClient.create(webTestClient, businessId, generateAccessToken());

        Customer customer = customerRepository.findById(randomCustomer.getId()).get();
        ShortUrl shortUrl = shortUrlRepository.findById(createMenuResponse.getShortUrlId()).get();

        assertTrue(customer.findMenu(businessId, shortUrl.getMenus().get(0)).isPresent());
        assertEquals(3, customer.firstBusiness().getMenus().size());
        assertEquals(3, shortUrl.getMenus().size());
        assertEquals(createMenuResponse.getShortUrlId(), createMenuResponse2.getShortUrlId());
        assertEquals(createMenuResponse.getShortUrlId(), createMenuResponse3.getShortUrlId());
        assertTrue(customer.firstBusiness().getMenus().stream().anyMatch(m -> m.getId().equals(shortUrl.getMenus().get(0))));
        assertTrue(customer.firstBusiness().getMenus().stream().anyMatch(m -> m.getId().equals(shortUrl.getMenus().get(1))));
        assertTrue(customer.firstBusiness().getMenus().stream().anyMatch(m -> m.getId().equals(shortUrl.getMenus().get(2))));
    }

    @Test
    void aCustomerCouldCreateAMenuAndHasAllFields() {
        customerRepository.save(randomCustomer);
        String businessId = randomCustomer.firstBusiness().getId();

        CreateMenuRequest createMenuRequest = MenuRequestHelper.withFullFields(businessId);
        CreateMenuResponse createMenuResponse = MenuWebClient.create(webTestClient, createMenuRequest, generateAccessToken());

        Customer customer = customerRepository.findById(randomCustomer.getId()).get();
        ShortUrl shortUrl = shortUrlRepository.findById(createMenuResponse.getShortUrlId()).get();
        Menu menu = customer.getBusinesses().get(0).getMenus().get(0);

        assertTrue(customer.findMenu(businessId, shortUrl.getMenus().get(0)).isPresent());
        assertEquals(createMenuRequest.getImageUrl(), menu.getImageUrl());
        assertEquals(createMenuRequest.getCurrency(), menu.getCurrency().getCurrencyCode());
        assertEquals(createMenuRequest.getName(), menu.getName());
    }
}