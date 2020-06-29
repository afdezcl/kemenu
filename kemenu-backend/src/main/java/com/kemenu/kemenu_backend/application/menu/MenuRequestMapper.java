package com.kemenu.kemenu_backend.application.menu;

import com.kemenu.kemenu_backend.domain.model.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = "spring", nullValueCheckStrategy = ALWAYS, injectionStrategy = CONSTRUCTOR)
public interface MenuRequestMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    Menu from(CreateMenuRequest createMenuRequest);

    @Mapping(source = "menuId", target = "id")
    Menu from(UpdateMenuRequest updateMenuRequest);
}
