package com.example.chulgunhazabackend.exception.employeeException;

import lombok.Getter;

@Getter
public enum EmployeeExceptionType {
    ALREADY_EXIST_EMAIL(404,"이미 존재하는 이메일입니다."),
    NOT_EXIST_USER(404, "존재하지 않는 사원입니다."),
    ALREADY_CHANGED(404, "사원 정보가 다른 사용자에 의해 변경되었습니다.")
    ;

    EmployeeExceptionType(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private final int status;
    private final String message;

}
