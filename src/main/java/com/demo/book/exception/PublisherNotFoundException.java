package com.demo.book.exception;

public class PublisherNotFoundException extends RuntimeException{
    public PublisherNotFoundException() {
        super("publisher not found");
    }
}
