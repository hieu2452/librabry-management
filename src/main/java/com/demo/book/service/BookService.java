package com.demo.book.service;

import com.demo.book.domain.BookFilter;
import com.demo.book.domain.PageableResponse;
import com.demo.book.entity.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {
    Book createBook(MultipartFile file, String model) throws IOException;
    PageableResponse<Book> findAll(BookFilter bookFilters);
    Book findById(long id);
    Book update(MultipartFile file, String model) throws IOException;
    List<Book> findByCategory(String type);
    void delete(long id);
    PageableResponse<Book> findByKeyword(BookFilter filter);
}
