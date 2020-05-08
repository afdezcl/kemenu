package com.kemenu.kemenu_backend.domain.model;

import com.kemenu.kemenu_backend.helper.BusinessHelper;
import com.kemenu.kemenu_backend.helper.CustomerHelper;
import com.kemenu.kemenu_backend.helper.MenuHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTest {

    @Test
    void aCustomerCouldCreateAMenu() {
        Customer customer = CustomerHelper.randomCustomer();
        Business business = BusinessHelper.randomBusiness();
        customer.createMenu(business, MenuHelper.randomMenu());
        customer.createMenu(business, MenuHelper.randomMenu());
        assertEquals(2, customer.menuList(business).size());
    }
}