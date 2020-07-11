package com.kemenu.kemenu_backend.infrastructure.mongo.converter;

import org.bson.Document;
import org.springframework.core.convert.converter.Converter;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

public class CurrencyReadConverter implements Converter<Document, CurrencyUnit> {
    @Override
    public CurrencyUnit convert(Document document) {
        return Monetary.getCurrency(document.getString("code"));
    }
}
