package com.demo.book.exception;

public class LibraryCardNotFound extends RuntimeException{
    public LibraryCardNotFound(String message) {
        super(message);
    }
}
