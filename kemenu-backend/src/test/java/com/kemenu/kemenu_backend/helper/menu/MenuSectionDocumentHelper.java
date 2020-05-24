package com.kemenu.kemenu_backend.helper.menu;

import com.kemenu.kemenu_backend.domain.model.MenuSection;
import com.kemenu.kemenu_backend.helper.dish.DishDocumentHelper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.Document;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuSectionDocumentHelper {

    public static Document version0(MenuSection menuSection) {
        Document documentV0 = new Document();
        documentV0.append("name", menuSection.getName());
        documentV0.append("dishes", menuSection.getDishes().stream().map(DishDocumentHelper::version0).collect(toList()));
        return documentV0;
    }
}
