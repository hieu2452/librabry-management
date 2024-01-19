package com.demo.book.adapter;

import com.demo.book.entity.Book;

import java.util.List;

public interface BookLanguage {
    List<Book> getBookByTypeAndLanguage(String type, String language);
}
