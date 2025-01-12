package com.example.chulgunhazabackend.domain;

import lombok.Getter;

@Getter
public enum UserRole {


    USER("사원"),
    MANAGER("근태 관리자"),
    ADMIN("관리자");


    private final String role;
    UserRole(String role) {
        this.role = role;
    }
}
