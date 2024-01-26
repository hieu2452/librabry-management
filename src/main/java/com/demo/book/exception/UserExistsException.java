package com.demo.book.exception;

public class UserExistsException extends RuntimeException{
    public UserExistsException() {
        super("User already exits");
    }
}
