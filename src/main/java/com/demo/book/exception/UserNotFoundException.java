package com.demo.book.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(long id) {
        super("User not found id " + id);
    }
}
