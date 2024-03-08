package com.demo.book.service.impl;

import com.demo.book.domain.dto.BookDto;
import com.demo.book.domain.params.BookFilter;
import com.demo.book.domain.response.PageableResponse;
import com.demo.book.service.BookRedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Service;

import java.util.Objects;

import java.util.concurrent.TimeUnit;

@Service
public class BookRedisServiceImpl implements BookRedisService {
    private static final Logger log = LoggerFactory.getLogger(BookRedisServiceImpl.class);
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

        return String.format("books-lst:%s:%s:%s:%s:%d:%d:%s",category,publisher,language,keyword,pageSize,pageNumber,sortDirection);
    }

    @Override
    public String getKey(long id) {
        return String.format("book:%d",id);
    }

    @Override
    public BookDto findById(long id) throws JsonProcessingException {
        try {
            String key = getKey(id);
            String json = (String) redisTemplate.opsForValue().get(key);

            return json != null ?
                    objectMapper.readValue(json, new TypeReference<BookDto>() {})
                    :
                    null;
        } catch (RedisConnectionFailureException ex) {
            log.error("Connect to redis server fail");
            return null;
        }
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
        try {
            String key = getKey(bookDto.getId());
            String json = objectMapper.writeValueAsString(bookDto);
            redisTemplate.opsForValue().set(key,json,20,TimeUnit.MINUTES);
        } catch (RedisConnectionFailureException ex) {
            log.error("Connect to redis server fail");
        }
    }

    @Override
    public void deleteCache(long id) {
        try {
            String key = getKey(id);
            if(key != null) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (RedisConnectionFailureException ex) {
            log.error("Connect to redis server fail");
        }
    }

    @Override
    public void clear() {
        Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().flushAll();
    }

//    public void deleteKeys(String pattern) {
//        Cursor<byte[]> cursor = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection()
//                .scan(ScanOptions.scanOptions()
//                        .match(pattern)
//                        .build());
//        while (cursor.hasNext()) {
//            byte[] keyBytes = cursor.next();
//            String key = new String(keyBytes, StandardCharsets.UTF_8);
//            redisTemplate.delete(key);
//        }
//        cursor.close();
//    }
}
