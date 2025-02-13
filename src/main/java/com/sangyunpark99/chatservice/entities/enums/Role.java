package com.sangyunpark99.chatservice.entities.enums;

import java.util.Arrays;

public enum Role {
    ROLE_USER("ROLE_USER"), ROLE_CONSULTANT("ROLE_CONSULTANT");

    private String code;

    Role(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static Role fromCode(String code) {
        return Arrays.stream(Role.values())
                .filter(role -> role.getCode().equals(code))
                .findFirst()
                .orElseThrow();
    }
}
