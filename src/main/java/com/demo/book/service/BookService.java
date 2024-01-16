package com.demo.book.service;

import com.demo.book.entity.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {
    Book createBook(MultipartFile file, String model) throws IOException;
    List<Book> findAll();
    Book findById(long id);
}
