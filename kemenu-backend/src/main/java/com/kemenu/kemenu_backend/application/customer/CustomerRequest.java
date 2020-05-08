package com.kemenu.kemenu_backend.application.customer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = CustomerRequest.CustomerRequestBuilder.class)
public class CustomerRequest {
    String businessName;
    String email;
    String password;
}
