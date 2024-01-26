package com.demo.book.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(long id) {
        super("User not found id " + id);
    }
}
