package com.demo.book.exception;

public class TokenRefreshException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TokenRefreshException(String token, String message) {
        super(message + " Token : " + token);
    }
}

