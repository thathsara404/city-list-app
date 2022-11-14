package com.city.list.enums;

import org.springframework.security.core.GrantedAuthority;

public enum CityFilter implements GrantedAuthority {

    CITY_NAME("city_name", 0);

    private String description;
    private Integer code;

    CityFilter(String description, Integer code) {
        this.description = description;
        this.code = code;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public String getAuthority() {
        return description;
    }
}
