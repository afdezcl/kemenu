package com.kemenu.kemenu_backend.application.customer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kemenu.kemenu_backend.application.business.BusinessResponse;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = CustomerResponse.CustomerResponseBuilder.class)
public class CustomerResponse {
    String id;
    List<BusinessResponse> businesses;
}
