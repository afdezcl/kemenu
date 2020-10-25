package com.kemenu.kemenu_backend.application.customer;

import com.kemenu.kemenu_backend.application.security.IntrospectiveService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Locale;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/web/v1")
class CustomerWebController {

    private final IntrospectiveService introspectiveService;
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping("/customer/{email}")
    ResponseEntity<CustomerResponse> read(@RequestHeader(value = "Authorization") String token,
                                          @PathVariable String email,
                                          Locale locale) {
        return introspectiveService.doCallOnMe(token, email, () -> customerService
                .read(email)
                .map(c -> ResponseEntity.ok(customerMapper.from(c, locale)))
                .orElseGet(() -> ResponseEntity.notFound().build())
        );
    }

    @PatchMapping("/customer/{email}/password/change")
    ResponseEntity<UUID> changePassword(@RequestHeader(value = "Authorization") String token,
                                        @PathVariable String email,
                                        @RequestBody @Valid PasswordChangeRequest passwordChangeRequest) {
        return introspectiveService.doCallOnMe(token, email, () -> customerService
                .changePassword(email, passwordChangeRequest.getPassword())
                .map(c -> ResponseEntity.ok(UUID.fromString(c)))
                .orElseGet(() -> ResponseEntity.notFound().build())
        );
    }

    @PatchMapping("/customer/{email}/marketing")
    ResponseEntity<UUID> changeMarketing(@RequestHeader(value = "Authorization") String token,
                                         @PathVariable String email,
                                         @RequestBody @Valid CustomerMarketingRequest customerMarketingRequest) {
        return introspectiveService.doCallOnMe(token, email, () -> customerService
                .changeMarketing(email, customerMarketingRequest)
                .map(c -> ResponseEntity.ok(UUID.fromString(c)))
                .orElseGet(() -> ResponseEntity.notFound().build())
        );
    }

    @DeleteMapping("/customer/{email}/business/{businessId}/menus/{menuId}")
    ResponseEntity<String> delete(@RequestHeader(value = "Authorization") String token,
                                  @PathVariable String email,
                                  @PathVariable String businessId,
                                  @PathVariable String menuId) {
        return introspectiveService.doCallOnMe(token, email, () -> {
            customerService.deleteMenu(email, businessId, menuId);
            log.info("Customer {} with business {} deleted {} menu", email, businessId, menuId);
            return ResponseEntity.ok("");
        });
    }
}
