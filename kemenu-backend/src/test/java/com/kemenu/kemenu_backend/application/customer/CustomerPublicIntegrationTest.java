package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.application.menu.CreateMenuResponse;
import com.kemenu.kemenu_backend.application.menu.MenuResponse;
import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.helper.menu.MenuWebClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerPublicIntegrationTest extends KemenuIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Test
    void anUnauthorizedUserCouldSeeAMenu() {
        customerRepository.save(randomCustomer);
        String businessId = randomCustomer.firstBusiness().getId();

        CreateMenuResponse createMenuResponse = MenuWebClient.create(webTestClient, businessId, generateAccessToken());

        String uri = "/show/" + createMenuResponse.getShortUrlId();
        HttpHeaders headers = webTestClient
                .get().uri(uri)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().exists("Set-Cookie")
                .expectBody().returnResult().getResponseHeaders();

        String shortUrlId = new String(Base64.getDecoder().decode(headers.get("Set-Cookie").get(0).replace("show_menu=", "")));
        MenuResponse menuResponse = customerService.readMenu(shortUrlId).get();

        assertEquals(randomCustomer.firstBusiness().getName(), menuResponse.getBusinessName());
        assertEquals(MenuWebClient.request.getSections().get(0).getName(), menuResponse.getSections().get(0).getName());
    }
}