package com.kemenu.kemenu_backend.domain.model;

import com.kemenu.kemenu_backend.helper.business.BusinessHelper;
import com.kemenu.kemenu_backend.helper.customer.CustomerHelper;
import com.kemenu.kemenu_backend.helper.menu.MenuHelper;
import org.junit.jupiter.api.Test;

import java.util.List;
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

        Menu updatedMenu = customer.changeMenu(business.getId(), newMenu).get();

        assertEquals(newMenu.getId(), updatedMenu.getId());
        assertNotEquals(oldMenu.getSections().get(0).getName(), customer.findMenu(business.getId(), updatedMenu.getId()).get().getSections().get(0).getName());
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

        Optional<Menu> optionalMenu = customer.changeMenu(UUID.randomUUID().toString(), menu);

        assertTrue(optionalMenu.isEmpty());
    }

    @Test
    void aCustomerCouldChangeABusiness() {
        Customer customer = CustomerHelper.withMenu();
        Business business = customer.firstBusiness();
        Business newBusiness = BusinessHelper.randomFrom(business.getId(), business.getMenus());

        customer.changeBusiness(newBusiness);
        Business updatedBusiness = customer.findBusiness(newBusiness.getId()).get();

        assertEquals(newBusiness.getName(), updatedBusiness.getName());
        assertEquals(newBusiness.getImageUrl(), updatedBusiness.getImageUrl());
        assertEquals(newBusiness.getPhone(), updatedBusiness.getPhone());
        assertEquals(newBusiness.getInfo(), updatedBusiness.getInfo());
        assertEquals(business.getMenus().get(0), updatedBusiness.getMenus().get(0));
        assertEquals(updatedBusiness.getMenus().size(), business.getMenus().size());
    }

    @Test
    void aCustomerCannotChangeTheBusinessNameOfUnknown() {
        Customer customer = CustomerHelper.randomCustomer();
        Business business = customer.firstBusiness();

        Business unknown = BusinessHelper.randomFrom(UUID.randomUUID().toString(), List.of());
        Optional<String> optionalBusinessId = customer.changeBusiness(unknown);

        assertTrue(optionalBusinessId.isEmpty());
        assertNotEquals(unknown.getName(), business.getName());
    }

    @Test
    void aCustomerCanDeleteAMenu() {
        Customer customer = CustomerHelper.randomCustomer();
        Business business = customer.firstBusiness();
        Menu menu = MenuHelper.randomMenu();

        String menuId = customer.createMenu(business.getId(), menu).get();

        assertEquals(1, customer.firstBusiness().getMenus().size());

        customer.deleteMenu(business.getId(), menuId);

        assertEquals(0, customer.firstBusiness().getMenus().size());
    }
}
