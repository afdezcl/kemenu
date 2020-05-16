package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.application.menu.MenuMapper;
import com.kemenu.kemenu_backend.application.menu.MenuResponse;
import com.kemenu.kemenu_backend.domain.model.Business;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.domain.model.Menu;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final MenuMapper menuMapper;

    public String create(Customer customer) {
        return customerRepository.save(customer); // TODO: Send event to send email
    }

    public Optional<Customer> read(String email) {
        return customerRepository.findByEmail(email);
    }

    public List<Customer> all() {
        return customerRepository.all();
    }

    public Optional<MenuResponse> readMenu(String customerId, String businessId, String menuId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        if (optionalCustomer.isEmpty()) {
            return Optional.empty();
        }

        Customer customer = optionalCustomer.get();
        Optional<Business> optionalBusiness = customer.findBusiness(businessId);

        if (optionalBusiness.isEmpty()) {
            return Optional.empty();
        }

        Business business = optionalBusiness.get();

        Optional<Menu> optionalMenu = business.findMenu(menuId);

        if (optionalMenu.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(menuMapper.from(business.getName(), optionalMenu.get()));
    }
}
