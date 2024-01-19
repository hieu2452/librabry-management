package com.demo.book.factory;

import com.demo.book.dto.BookDto;
import com.demo.book.entity.Book;

import java.util.List;
import java.util.Optional;

public interface IBook {
    Book createBook(BookDto BookDto);
    Book getByCategory(String category);
    Book updateBook(Book book);
    void deleteBook(long id);
    Optional<Book> findBookById(long id);
}