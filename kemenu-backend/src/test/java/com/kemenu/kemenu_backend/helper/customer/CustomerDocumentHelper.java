package com.kemenu.kemenu_backend.helper.customer;

import com.kemenu.kemenu_backend.domain.model.Customer;
import com.kemenu.kemenu_backend.helper.business.BusinessDocumentHelper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.Document;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerDocumentHelper {

    public static Document version0(Customer customer) {
        Document documentV0 = new Document();
        documentV0.append("_id", customer.getId());
        documentV0.append("email", customer.getEmail());
        documentV0.append("password", customer.getPassword());
        documentV0.append("businesses", customer.getBusinesses().stream().map(BusinessDocumentHelper::version0).collect(toList()));
        documentV0.append("role", customer.getRole());
        documentV0.append("_class", customer.getClass().getCanonicalName());
        return documentV0;
    }
}
