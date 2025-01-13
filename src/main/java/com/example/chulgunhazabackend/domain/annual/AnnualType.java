package com.example.chulgunhazabackend.domain.annual;

import lombok.Getter;

@Getter
public enum AnnualType {
    ANNUAL("연차"),
    ANNUAL_AM("오전 반차"),
    ANNUAL_PM("오후 반차")
    ;

    private final String value;

    AnnualType(String value) {
        this.value = value;
    }
}
