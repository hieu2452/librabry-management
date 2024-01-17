package com.demo.book.factory;

import com.demo.book.entity.Book;
import com.demo.book.entity.LecturerBook;

import java.util.List;

public interface IBook<T> {
    List<T> getBook();
}
