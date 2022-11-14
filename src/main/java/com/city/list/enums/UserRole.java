package com.city.list.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    USER("user", 0),
    ADMIN("admin", 1);

    private String description;
    private Integer code;

    UserRole(String description, Integer code) {
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
