package com.demo.book.service;

import com.demo.book.domain.dto.BookDto;
import com.demo.book.domain.params.BookFilter;
import com.demo.book.domain.response.PageableResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.PageRequest;

public interface BookRedisService {

    String getKey(BookFilter bookFilter, PageRequest pageRequest);
    String getKey(long id);
    BookDto findById(long id) throws JsonProcessingException;

    PageableResponse<BookDto> findAll(BookFilter bookFilter, PageRequest pageRequest) throws JsonProcessingException;
    void saveAll(PageableResponse<BookDto> bookResponse,BookFilter bookFilter , PageRequest pageRequest) throws JsonProcessingException;
    void save(BookDto bookDto) throws JsonProcessingException;
    void deleteCache(long id);
    void clear();
}
