package com.kemenu.kemenu_backend.infrastructure.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.IndexResolver;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MongoIndexCreationListener {

    private final MongoTemplate mongoTemplate;

    @EventListener(ContextRefreshedEvent.class)
    public void initIndicesAfterStartup() {
        var mappingContext = mongoTemplate.getConverter().getMappingContext();
        IndexResolver resolver = new MongoPersistentEntityIndexResolver(mappingContext);

        mappingContext.getPersistentEntities()
                .stream()
                .filter(it -> it.isAnnotationPresent(Document.class))
                .forEach(it -> {
                    IndexOperations indexOps = mongoTemplate.indexOps(it.getType());
                    resolver.resolveIndexFor(it.getType()).forEach(indexOps::ensureIndex);
                });
    }
}
