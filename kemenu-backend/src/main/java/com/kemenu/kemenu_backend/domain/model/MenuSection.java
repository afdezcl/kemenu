package com.kemenu.kemenu_backend.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class MenuSection {
    String name;
}
