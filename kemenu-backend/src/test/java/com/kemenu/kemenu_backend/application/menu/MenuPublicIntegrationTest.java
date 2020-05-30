package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.helper.menu.MenuPublicClient;
import com.kemenu.kemenu_backend.helper.menu.MenuWebClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuPublicIntegrationTest extends KemenuIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void aPublicUserCouldReadMenus() {
        customerRepository.save(randomCustomer);
        String businessId = randomCustomer.firstBusiness().getId();

        CreateMenuResponse createMenuResponse = MenuWebClient.create(webTestClient, businessId, generateAccessToken());
        MenuWebClient.create(webTestClient, businessId, generateAccessToken());
        MenuWebClient.create(webTestClient, businessId, generateAccessToken());

        Customer customer = customerRepository.findById(randomCustomer.getId()).get();
        List<Menu> savedMenus = customer.firstBusiness().getMenus();
        List<MenuResponse> requestedMenus = MenuPublicClient.read(webTestClient, createMenuResponse.getShortUrlId());

        assertTrue(requestedMenus.stream().allMatch(m -> m.getShortUrlId().equals(createMenuResponse.getShortUrlId())));
        assertEquals(3, requestedMenus.size());
        assertEquals(savedMenus.get(0).getId(), requestedMenus.get(0).getId());
        assertEquals(savedMenus.get(1).getId(), requestedMenus.get(1).getId());
        assertEquals(savedMenus.get(2).getId(), requestedMenus.get(2).getId());
    }
}
