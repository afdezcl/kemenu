package com.kemenu.kemenu_backend.infrastructure.mongo.converter;

import org.bson.Document;
import org.springframework.core.convert.converter.Converter;

import javax.money.CurrencyUnit;

public class CurrencyWriteConverter implements Converter<CurrencyUnit, Document> {
    @Override
    public Document convert(CurrencyUnit currencyUnit) {
        Document currencyDocument = new Document();
        currencyDocument.append("code", currencyUnit.getCurrencyCode());
        return currencyDocument;
    }
}
