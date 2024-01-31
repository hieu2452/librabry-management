package com.demo.book.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(long id) {
        super("Book not found id: " + id);
    }
}
