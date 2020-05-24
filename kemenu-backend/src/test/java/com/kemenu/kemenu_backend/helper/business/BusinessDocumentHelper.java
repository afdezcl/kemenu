package com.kemenu.kemenu_backend.helper.business;

import com.kemenu.kemenu_backend.domain.model.Business;
import com.kemenu.kemenu_backend.helper.menu.MenuDocumentHelper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.Document;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BusinessDocumentHelper {

    public static Document version0(Business business) {
        Document documentV0 = new Document();
        documentV0.append("_id", business.getId());
        documentV0.append("name", business.getName());
        documentV0.append("menus", business.getMenus().stream().map(MenuDocumentHelper::version0).collect(toList()));
        return documentV0;
    }
}
