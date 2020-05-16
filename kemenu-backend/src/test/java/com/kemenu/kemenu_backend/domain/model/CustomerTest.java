package com.kemenu.kemenu_backend.domain.model;

import com.kemenu.kemenu_backend.helper.CustomerHelper;
import com.kemenu.kemenu_backend.helper.MenuHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerTest {

    @Test
    void aCustomerCouldCreateAMenu() {
        Customer customer = CustomerHelper.randomCustomer();
        Business business = customer.firstBusiness();
        Menu menu = MenuHelper.randomMenu();

        String menuId = customer.createMenu(business, menu);

        assertEquals(menu.getId(), menuId);
        assertTrue(customer.findMenu(business.getId(), menu.getId()).isPresent());
    }

    @Test
    void aCustomerCouldChangeAMenu() {
        Customer customer = CustomerHelper.randomCustomer();
        Business business = customer.firstBusiness();
        Menu oldMenu = MenuHelper.randomMenu();

        String oldMenuId = customer.createMenu(business, oldMenu);

        Menu newMenu = MenuHelper.from(oldMenuId, MenuHelper.randomMenu().getSections());

        String menuId = customer.changeMenu(business, newMenu);

        assertEquals(newMenu.getId(), menuId);
        assertNotEquals(oldMenu, customer.findMenu(business.getId(), menuId).get());
    }
}