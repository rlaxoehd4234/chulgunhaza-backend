package com.example.chulgunhazabackend.exception;

import lombok.Getter;

@Getter
public class PostException extends RuntimeException {

    private final PostExceptionType postExceptionType;

    public PostException(PostExceptionType postExceptionType) {
        super(postExceptionType.getMessage());
        this.postExceptionType = postExceptionType;
    }

}
