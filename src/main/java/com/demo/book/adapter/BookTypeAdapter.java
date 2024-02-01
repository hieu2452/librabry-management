package com.demo.book.adapter;

import com.demo.book.entity.Book;
import com.demo.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookTypeAdapter implements BookLanguage{
    @Autowired
    private BookService bookService;
    @Override
    public List<Book> getBookByTypeAndLanguage(String type, String language) {
//        List<Book> books = bookService.findByCategory(type);

        return null;
    }
}
