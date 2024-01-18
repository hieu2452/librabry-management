package com.demo.book.dto;

import com.demo.book.entity.Book;

import java.util.List;

public class BillDto {
    private long userId;
    private List<BookDto> books;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<BookDto> getBooks() {
        return books;
    }

    public void setBooks(List<BookDto> books) {
        this.books = books;
    }
}
