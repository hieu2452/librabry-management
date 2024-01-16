package com.demo.book.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BookService {
    void createbook(MultipartFile file, String model) throws IOException;
}
