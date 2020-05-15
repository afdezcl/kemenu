package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.domain.model.Business;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.domain.model.Menu;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.BiFunction;

@Service
@AllArgsConstructor
public class MenuService {

    private final CustomerRepository customerRepository;
    private final MenuMapper menuMapper;

    public Optional<String> create(String customerEmail, String businessId, Menu menu) {
        return save(
                customerEmail,
                businessId,
                (c, b) -> c.createMenu(b, menu)
        );
    }

    public Optional<String> update(String customerEmail, UpdateMenuRequest updateMenuRequest) {
        return save(
                customerEmail,
                updateMenuRequest.getBusinessId(),
                (c, b) -> c.changeMenu(b, menuMapper.from(updateMenuRequest))
        );
    }

    private Optional<String> save(String customerEmail,
                                  String businessId,
                                  BiFunction<Customer, Business, String> action) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(customerEmail);

        if (optionalCustomer.isEmpty()) {
            return Optional.empty();
        }

        Customer customer = optionalCustomer.get();
        Optional<Business> optionalBusiness = customer.findBusiness(businessId);

        if (optionalBusiness.isEmpty()) {
            return Optional.empty();
        }

        String menuId = action.apply(customer, optionalBusiness.get());

        customerRepository.save(customer);

        return Optional.of(menuId);
    }
}
