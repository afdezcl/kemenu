package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.application.business.BusinessResponse;
import com.kemenu.kemenu_backend.application.business.UpdateBusinessRequest;
import com.kemenu.kemenu_backend.application.menu.CreateMenuResponse;
import com.kemenu.kemenu_backend.common.KemenuIntegrationTest;
import com.kemenu.kemenu_backend.domain.model.Business;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.domain.model.NewsletterStatus;
import com.kemenu.kemenu_backend.domain.model.ShortUrl;
import com.kemenu.kemenu_backend.domain.model.ShortUrlRepository;
import com.kemenu.kemenu_backend.helper.business.UpdateBusinessRequestHelper;
import com.kemenu.kemenu_backend.helper.customer.CustomerHelper;
import com.kemenu.kemenu_backend.helper.customer.PasswordChangeRequestHelper;
import com.kemenu.kemenu_backend.helper.menu.MenuWebClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerWebIntegrationTest extends KemenuIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Test
    void aCustomerCanFindHimself() {
        Customer customer = CustomerHelper.withMenu();
        customerRepository.save(customer);

        CustomerResponse customerResponse = webTestClient
                .get().uri("/web/v1/customer/" + customer.getEmail())
                .header("Authorization", generateAccessToken(customer.getEmail()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerResponse.class).returnResult().getResponseBody();

        assertEquals(customer.firstBusiness().getName(), customerResponse.getBusinesses().get(0).getName());
    }

    @Test
    void aCustomerCantFindInformationAboutOtherCustomer() {
        webTestClient
                .get().uri("/web/v1/customer/other@customer.com")
                .header("Authorization", generateAccessToken())
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void aCustomerCouldChangeHisPassword() {
        Customer customer = CustomerHelper.withMenu();
        String oldPassword = customer.getPassword();
        String customerId = customerRepository.save(customer);
        PasswordChangeRequest passwordChangeRequest = PasswordChangeRequestHelper.random();

        String customerIdResponse = webTestClient
                .patch().uri("/web/v1/customer/" + customer.getEmail() + "/password/change")
                .body(Mono.just(passwordChangeRequest), PasswordChangeRequest.class)
                .header("Authorization", generateAccessToken(customer.getEmail()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody().toString();

        Customer updatedCustomer = customerRepository.findById(customerIdResponse).get();

        assertEquals(customerId, customerIdResponse);
        assertNotEquals(oldPassword, updatedCustomer.getPassword());
    }

    @Test
    void aCustomerCannotChangeHisPasswordIfThePasswordsDontMatch() {
        webTestClient
                .patch().uri("/web/v1/customer/test@example.com/password/change")
                .body(Mono.just(PasswordChangeRequestHelper.notSamePassword()), PasswordChangeRequest.class)
                .header("Authorization", generateAccessToken("test@example.com"))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void aCustomerCouldChangeABusiness() {
        Customer customer = CustomerHelper.withMenu();
        Business business = customer.firstBusiness();
        String customerId = customerRepository.save(customer);
        var request = UpdateBusinessRequestHelper.random();

        String customerIdResponse = webTestClient
                .put().uri("/web/v1/customer/" + customer.getEmail() + "/business/" + business.getId())
                .body(Mono.just(request), UpdateBusinessRequest.class)
                .header("Authorization", generateAccessToken(customer.getEmail()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody().toString();

        CustomerResponse customerResponse = webTestClient
                .get().uri("/web/v1/customer/" + customer.getEmail())
                .header("Authorization", generateAccessToken(customer.getEmail()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerResponse.class).returnResult().getResponseBody();

        BusinessResponse businessResponse = customerResponse.getBusinesses().get(0);

        Customer updatedCustomer = customerRepository.findById(customerIdResponse).get();
        Business updatedBusiness = updatedCustomer.firstBusiness();

        assertEquals(customerId, customerIdResponse);
        assertNotEquals(business.getName(), businessResponse.getName());
        assertEquals(business.getId(), businessResponse.getId());
        assertEquals(request.getName(), businessResponse.getName());
        assertEquals(request.getImageUrl(), businessResponse.getImageUrl());
        assertEquals(request.getPhone(), businessResponse.getPhone());
        assertEquals(request.getInfo(), businessResponse.getInfo());
        assertEquals(business.getMenus().get(0), updatedBusiness.getMenus().get(0));
        assertEquals(updatedBusiness.getMenus().size(), businessResponse.getMenus().size());
    }

    @Test
    void aCustomerCouldNotChangeAnUnknownBusiness() {
        Customer customer = CustomerHelper.withMenu();
        customerRepository.save(customer);
        var request = UpdateBusinessRequestHelper.random();

        webTestClient
                .put().uri("/web/v1/customer/" + customer.getEmail() + "/business/" + UUID.randomUUID().toString())
                .body(Mono.just(request), UpdateBusinessRequest.class)
                .header("Authorization", generateAccessToken(customer.getEmail()))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void aCustomerCouldChangeHisNewsletterInfo() {
        Customer customer = CustomerHelper.withMenu();
        customerRepository.save(customer);

        CustomerResponse registerCustomer = webTestClient
                .get().uri("/web/v1/customer/" + customer.getEmail())
                .header("Authorization", generateAccessToken(customer.getEmail()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerResponse.class).returnResult().getResponseBody();

        assertTrue(registerCustomer.getNewsletterStatus().isAccepted());

        String customerIdResponse = webTestClient
                .patch().uri("/web/v1/customer/" + customer.getEmail() + "/marketing")
                .body(Mono.just(CustomerMarketingRequest.builder().newsletterStatus(NewsletterStatus.REJECTED).build()), CustomerMarketingRequest.class)
                .header("Authorization", generateAccessToken(customer.getEmail()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UUID.class).returnResult().getResponseBody().toString();

        Customer updatedCustomer = customerRepository.findById(customerIdResponse).get();

        assertTrue(updatedCustomer.getMarketingInfo().getNewsletterStatus().isRejected());
    }

    @Test
    void aCustomerCouldNotChangeHisNewsletterInfoToOld() {
        Customer customer = CustomerHelper.withMenu();
        customerRepository.save(customer);

        webTestClient
                .patch().uri("/web/v1/customer/" + customer.getEmail() + "/marketing")
                .body(Mono.just(CustomerMarketingRequest.builder().newsletterStatus(NewsletterStatus.OLD).build()), CustomerMarketingRequest.class)
                .header("Authorization", generateAccessToken(customer.getEmail()))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void aCustomerCouldDeleteAMenu() {
        customerRepository.save(randomCustomer);
        String businessId = randomCustomer.firstBusiness().getId();

        CreateMenuResponse createMenuResponse = MenuWebClient.create(webTestClient, businessId, generateAccessToken());

        Customer customer = customerRepository.findById(randomCustomer.getId()).get();
        ShortUrl shortUrl = shortUrlRepository.findById(createMenuResponse.getShortUrlId()).get();

        Menu menu = customer.findMenu(businessId, shortUrl.getMenus().get(0)).get();

        webTestClient
                .delete().uri("/web/v1/customer/" + customer.getEmail() + "/business/" + businessId + "/menus/" + menu.getId())
                .header("Authorization", generateAccessToken(customer.getEmail()))
                .exchange()
                .expectStatus().isOk();

        Customer customerWithDeletedMenu = customerRepository.findById(randomCustomer.getId()).get();
        assertEquals(0, customerWithDeletedMenu.findBusiness(businessId).get().getMenus().size());
    }
}