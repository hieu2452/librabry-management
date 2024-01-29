package com.demo.book.exception;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException(String message) {        super(message);
    }
}
