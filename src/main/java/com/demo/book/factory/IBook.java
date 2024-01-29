package com.demo.book.factory;

import com.demo.book.domain.BookDto;
import com.demo.book.entity.Book;

import java.util.Optional;

public interface IBook {
    Book createBook(BookDto BookDto);
    Book getByCategory(String category);
    Book updateBook(BookDto book);
    void deleteBook(long id);
    Optional<Book> findBookById(long id);
}
