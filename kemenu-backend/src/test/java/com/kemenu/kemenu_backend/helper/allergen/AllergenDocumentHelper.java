package com.kemenu.kemenu_backend.helper.allergen;

import com.kemenu.kemenu_backend.domain.model.Allergen;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.Document;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AllergenDocumentHelper {

    public static Document version0(Allergen allergen) {
        Document documentV0 = new Document();
        documentV0.append("_id", allergen.getId());
        documentV0.append("name", allergen.getName());
        return documentV0;
    }
}
