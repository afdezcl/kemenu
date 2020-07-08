package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.domain.model.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = "spring", nullValueCheckStrategy = ALWAYS, injectionStrategy = CONSTRUCTOR)
public interface MenuRequestMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "currency", expression = "java(javax.money.Monetary.getCurrency(java.util.Objects.isNull(c.getCurrency()) ? \"EUR\" : c.getCurrency()))")
    Menu from(CreateMenuRequest c);

    @Mapping(source = "menuId", target = "id")
    @Mapping(target = "currency", expression = "java(javax.money.Monetary.getCurrency(java.util.Objects.isNull(u.getCurrency()) ? \"EUR\" : u.getCurrency()))")
    Menu from(UpdateMenuRequest u);
}
