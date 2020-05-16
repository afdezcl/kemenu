package com.kemenu.kemenu_backend.application.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kemenu.kemenu_backend.application.menu.CreateMenuRequest;
import com.kemenu.kemenu_backend.application.menu.MenuResponse;
import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.helper.MenuRequestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerPublicIntegrationTest extends KemenuIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void anUnauthorizedUserCouldSeeAMenu() throws JsonProcessingException {
        customerRepository.save(randomCustomer);
        String businessId = randomCustomer.firstBusiness().getId();
        CreateMenuRequest createMenuRequest = MenuRequestHelper.randomRequest(businessId);

        UUID menuId = webTestClient
                .post().uri("/web/v1/menus")
                .body(Mono.just(createMenuRequest), CreateMenuRequest.class)
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody();

        String uri = "/customers/" + randomCustomer.getId() + "/businesses/" + businessId + "/menus/" + menuId;
        HttpHeaders headers = webTestClient
                .get().uri(uri)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().exists("Set-Cookie")
                .expectBody().returnResult().getResponseHeaders();

        String menuResponseJson = new String(Base64.getDecoder().decode(headers.get("Set-Cookie").get(0).replace("show_menu=", "")));
        MenuResponse menuResponse = mapper.readValue(menuResponseJson, MenuResponse.class);

        assertEquals(randomCustomer.firstBusiness().getName(), menuResponse.getBusinessName());
        assertEquals(createMenuRequest.getSections().get(0).getName(), menuResponse.getSections().get(0).getName());
    }
}