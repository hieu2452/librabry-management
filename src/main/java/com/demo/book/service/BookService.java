package com.demo.book.service;

import com.demo.book.domain.dto.BookDto;
import com.demo.book.domain.params.BookFilter;
import com.demo.book.domain.response.PageableResponse;
import com.demo.book.entity.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {
    BookDto createBook(BookDto bookDto);
    PageableResponse<BookDto> findAll(BookFilter bookFilters);
    BookDto findById(long id) throws JsonProcessingException;
    BookDto update(BookDto bookDto);
//    List<Book> findByCategory(String type);
    void delete(long id);
    PageableResponse<BookDto> findByKeyword(BookFilter filter);
}
