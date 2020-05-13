package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.domain.model.MenuRepository;
import com.kemenu.kemenu_backend.helper.MenuRequestHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuWebIntegrationTest extends KemenuIntegrationTest {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void aCustomerCouldCreateAMenu() {
        UUID menuId = webTestClient
                .post().uri("/web/v1/menus")
                .body(Mono.just(MenuRequestHelper.randomRequest(randomCustomer.getEmail())), MenuRequest.class)
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody();

        Menu createdMenu = menuRepository.findById(menuId.toString()).get();

        assertEquals(menuId.toString(), createdMenu.getId());
    }
}