package com.demo.book.service.impl;

import com.demo.book.domain.dto.BookDto;
import com.demo.book.domain.params.BookFilter;
import com.demo.book.domain.response.PageableResponse;
import com.demo.book.service.BookRedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class BookRedisServiceImpl implements BookRedisService {
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public String getKey(BookFilter bookFilter, PageRequest pageRequest) {
        String category = bookFilter.getCategory();
        String publisher = bookFilter.getPublisher();
        String language = bookFilter.getLanguage();
        String keyword = bookFilter.getKeyword();
        int pageSize = bookFilter.getPageSize();
        int pageNumber = bookFilter.getPageNumber();
        String sortDirection = pageRequest.getSort().getOrderFor("addedDate").getDirection()
                == Sort.Direction.DESC ? "desc" : "asc";

        return String.format("books:%s:%s:%s:%s:%d:%d:%s",category,publisher,language,keyword,pageSize,pageNumber,sortDirection);
    }

    @Override
    public String getKey(long id) {
        return String.format("book:%d",id);
    }

    @Override
    public BookDto findById(long id) throws JsonProcessingException {
        String key = getKey(id);
        String json = (String) redisTemplate.opsForValue().get(key);

        return json != null ?
                objectMapper.readValue(json, new TypeReference<BookDto>() {})
                :
                null;
    }

    @Override
    public PageableResponse<BookDto> findAll(BookFilter bookFilter, PageRequest pageRequest) throws JsonProcessingException {
        String key = getKey(bookFilter,pageRequest);
        String json = (String) redisTemplate.opsForValue().get(key);

        return json != null ?
                objectMapper.readValue(json, new TypeReference<PageableResponse<BookDto>>() {})
                :
                null;
    }

    @Override
    public void saveAll(PageableResponse<BookDto> bookResponse,BookFilter bookFilter, PageRequest pageRequest) throws JsonProcessingException {
        String key = getKey(bookFilter,pageRequest);
        String json = objectMapper.writeValueAsString(bookResponse);
        redisTemplate.opsForValue().set(key,json,10, TimeUnit.MINUTES);
    }

    @Override
    public void save(BookDto bookDto) throws JsonProcessingException {
        String key = getKey(bookDto.getId());
        String json = objectMapper.writeValueAsString(bookDto);
        redisTemplate.opsForValue().set(key,json,20,TimeUnit.MINUTES);
    }

    @Override
    public void clear() {
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().flushAll();
    }
}
