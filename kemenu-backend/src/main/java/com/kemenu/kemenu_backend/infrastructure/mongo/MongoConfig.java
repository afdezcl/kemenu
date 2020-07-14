package com.kemenu.kemenu_backend.infrastructure.mongo;

import com.kemenu.kemenu_backend.infrastructure.mongo.converter.CurrencyReadConverter;
import com.kemenu.kemenu_backend.infrastructure.mongo.converter.CurrencyWriteConverter;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    private final ConnectionString connectionString;

    public MongoConfig(@Value("${spring.data.mongodb.uri}") String mongoUri) {
        this.connectionString = new ConnectionString(mongoUri);
    }

    @Override
    protected String getDatabaseName() {
        return connectionString.getDatabase();
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        builder.applyConnectionString(connectionString);
    }

    @Override
    protected void configureConverters(MongoCustomConversions.MongoConverterConfigurationAdapter adapter) {
        adapter.registerConverter(new CurrencyReadConverter());
        adapter.registerConverter(new CurrencyWriteConverter());
    }
}
