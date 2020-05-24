package com.kemenu.kemenu_backend.helper.dish;

import com.kemenu.kemenu_backend.domain.model.Dish;
import com.kemenu.kemenu_backend.helper.allergen.AllergenDocumentHelper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.Document;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DishDocumentHelper {

    public static Document version0(Dish dish) {
        Document documentV0 = new Document();
        documentV0.append("name", dish.getName());
        documentV0.append("description", dish.getDescription());
        documentV0.append("price", dish.getPrice());
        documentV0.append("allergens", dish.getAllergens().stream().map(AllergenDocumentHelper::version0).collect(toList()));
        return documentV0;
    }
}
