package com.kemenu.kemenu_backend.domain.model;

import com.kemenu.kemenu_backend.helper.customer.CustomerHelper;
import com.kemenu.kemenu_backend.helper.menu.MenuHelper;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerTest {

    @Test
    void aCustomerCouldCreateAMenu() {
        Customer customer = CustomerHelper.randomCustomer();
        Business business = customer.firstBusiness();
        Menu menu = MenuHelper.randomMenu();

        String menuId = customer.createMenu(business.getId(), menu).get();

        assertEquals(menu.getId(), menuId);
        assertTrue(customer.findMenu(business.getId(), menu.getId()).isPresent());
    }

    @Test
    void aCustomerCouldChangeAMenu() {
        Customer customer = CustomerHelper.randomCustomer();
        Business business = customer.firstBusiness();
        Menu oldMenu = MenuHelper.randomMenu();

        String oldMenuId = customer.createMenu(business.getId(), oldMenu).get();

        Menu newMenu = MenuHelper.from(oldMenuId, MenuHelper.randomMenu().getSections());

        String menuId = customer.changeMenu(business.getId(), newMenu).get();

        assertEquals(newMenu.getId(), menuId);
        assertNotEquals(oldMenu.getSections().get(0).getName(), customer.findMenu(business.getId(), menuId).get().getSections().get(0).getName());
    }

    @Test
    void aCustomerCannotCreateAMenuForUnknownBusiness() {
        Customer customer = CustomerHelper.randomCustomer();
        Menu menu = MenuHelper.randomMenu();

        Optional<String> optionalMenuId = customer.createMenu(UUID.randomUUID().toString(), menu);

        assertTrue(optionalMenuId.isEmpty());
    }

    @Test
    void aCustomerCannotChangeAMenuForUnknownBusiness() {
        Customer customer = CustomerHelper.randomCustomer();
        Menu menu = MenuHelper.randomMenu();

        Optional<String> optionalMenuId = customer.changeMenu(UUID.randomUUID().toString(), menu);

        assertTrue(optionalMenuId.isEmpty());
    }

    @Test
    void aCustomerCouldChangeTheBusinessName() {
        Customer customer = CustomerHelper.randomCustomer();
        Business business = customer.firstBusiness();
        String newName = "newName";

        Optional<String> optionalNewName = customer.changeBusinessName(business.getId(), newName);

        assertEquals(newName, optionalNewName.get());
    }

    @Test
    void aCustomerCannotChangeTheBusinessNameOfUnknown() {
        Customer customer = CustomerHelper.randomCustomer();
        String newName = "newName";

        Optional<String> optionalNewName = customer.changeBusinessName(UUID.randomUUID().toString(), newName);

        assertTrue(optionalNewName.isEmpty());
        assertNotEquals(newName, customer.firstBusiness().getName());
    }
}
