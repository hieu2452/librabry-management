package com.demo.book.service;

import com.demo.book.domain.dto.BookDto;
import com.demo.book.domain.params.BookFilter;
import com.demo.book.domain.response.PageableResponse;
import com.demo.book.entity.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {
    Book createBook(BookDto bookDto);
    PageableResponse<Book> findAll(BookFilter bookFilters);
    Book findById(long id);
    Book update(BookDto bookDto);
//    List<Book> findByCategory(String type);
    void delete(long id);
    PageableResponse<Book> findByKeyword(BookFilter filter);
}
