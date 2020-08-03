package com.kemenu.kemenu_backend.application.customer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kemenu.kemenu_backend.application.validation.NewsletterAcceptedOrRejected;
import com.kemenu.kemenu_backend.domain.model.NewsletterStatus;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = CustomerMarketingRequest.CustomerMarketingRequestBuilder.class)
public class CustomerMarketingRequest {
    @NewsletterAcceptedOrRejected
    NewsletterStatus newsletterStatus;
}
