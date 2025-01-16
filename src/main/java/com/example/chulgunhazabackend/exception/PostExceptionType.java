package com.example.chulgunhazabackend.exception;

import lombok.Getter;

@Getter
public enum PostExceptionType {
    POST_NOT_FOUND(404,"존재하지 않는 게시글 입니다.");

    private final int status;
    private final String message;

    PostExceptionType(int status ,String message){
        this.status = status;
        this.message = message;
    }

}
