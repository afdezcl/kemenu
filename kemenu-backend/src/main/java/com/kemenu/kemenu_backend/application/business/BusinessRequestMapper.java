package com.kemenu.kemenu_backend.application.business;

import com.kemenu.kemenu_backend.domain.model.Business;
import com.kemenu.kemenu_backend.domain.model.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = "spring", nullValueCheckStrategy = ALWAYS, injectionStrategy = CONSTRUCTOR)
public interface BusinessRequestMapper {

    @Mapping(source = "businessId", target = "id")
    Business from(String businessId, List<Menu> menus, UpdateBusinessRequest updateBusinessRequest);
}
