package com.kemenu.kemenu_backend.application.security;

import com.kemenu.kemenu_backend.domain.model.ConfirmedEmail;
import com.kemenu.kemenu_backend.domain.model.ConfirmedEmailRepository;
import com.kemenu.kemenu_backend.domain.model.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final ConfirmedEmailRepository confirmedEmailRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return customerRepository.findByEmail(email)
                .map(customer -> {
                    if (hasConfirmedAccount(email)) {
                        return new User(customer.getEmail(), customer.getPassword(), List.of(customer));
                    } else {
                        log.info("Customer with email {} is not confirmed", email);
                        throw new UsernameNotFoundException("Invalid email or password.");
                    }
                })
                .orElseThrow(() -> {
                    log.info("Customer with email {} tried to login with incorrect email or password", email);
                    throw new UsernameNotFoundException("Invalid email or password.");
                });
    }

    private boolean hasConfirmedAccount(String email) {
        return confirmedEmailRepository.findByEmail(email)
                .map(ConfirmedEmail::isConfirmed)
                .orElse(false);
    }
}
