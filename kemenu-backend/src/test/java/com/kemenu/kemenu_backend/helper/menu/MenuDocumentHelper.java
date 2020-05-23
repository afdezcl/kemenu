package com.kemenu.kemenu_backend.helper.menu;

import com.kemenu.kemenu_backend.domain.model.Menu;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.Document;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuDocumentHelper {

    public static Document version0(Menu menu) {
        Document documentV0 = new Document();
        documentV0.append("_id", menu.getId());
        documentV0.append("sections", menu.getSections().stream().map(MenuSectionDocumentHelper::version0).collect(toList()));
        return documentV0;
    }
}
