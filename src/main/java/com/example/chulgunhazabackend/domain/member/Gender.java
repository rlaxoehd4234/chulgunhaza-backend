package com.example.chulgunhazabackend.domain.member;

import lombok.Getter;

@Getter
public enum Gender {

    MALE("남자"),
    FEMALE("여자")
    ;

    private final String value;

    Gender(String value) {
        this.value = value;
    }
}
