package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.domain.model.Business;
import com.kemenu.kemenu_backend.domain.model.BusinessRepository;
import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import com.kemenu.kemenu_backend.domain.model.Menu;
import com.kemenu.kemenu_backend.domain.model.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MenuService {

    private final BusinessRepository businessRepository;
    private final CustomerRepository customerRepository;
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    public Optional<String> create(String customerEmail, String businessId, Menu menu) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(customerEmail);
        Optional<Business> optionalBusiness = businessRepository.read(businessId);

        if (optionalCustomer.isEmpty() || optionalBusiness.isEmpty()) {
            return Optional.empty();
        }

        Customer customer = optionalCustomer.get();
        Business business = optionalBusiness.get();

        String menuId = customer.createMenu(business, menu);
        customerRepository.create(customer);

        return Optional.of(menuId);
    }

    public MenuResponse read(String menuId) {
        return menuRepository.findById(menuId)
                .map(menuMapper::from)
                .orElse(MenuResponse.builder().sections(List.of()).build());
    }

    public Optional<String> update(String customerEmail, UpdateMenuRequest updateMenuRequest) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(customerEmail);
        Optional<Business> optionalBusiness = businessRepository.read(updateMenuRequest.getBusinessId());

        if (optionalCustomer.isEmpty() || optionalBusiness.isEmpty()) {
            return Optional.empty();
        }

        Customer customer = optionalCustomer.get();
        Business business = optionalBusiness.get();

        Menu newMenuWithoutId = menuMapper.from(
                CreateMenuRequest.builder()
                        .businessId(updateMenuRequest.getBusinessId())
                        .sections(updateMenuRequest.getSections())
                        .build()
        );

        customer.changeMenu(business, new Menu(updateMenuRequest.getMenuId(), newMenuWithoutId.getSections()));
        customerRepository.create(customer);

        return Optional.of(updateMenuRequest.getMenuId());
    }
}
