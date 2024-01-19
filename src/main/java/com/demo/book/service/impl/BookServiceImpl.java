package com.demo.book.service.impl;

import com.demo.book.dto.BookDto;
import com.demo.book.entity.Book;
import com.demo.book.factory.ServiceAbstractFactory;
import com.demo.book.repository.BookRepository;
import com.demo.book.repository.CategoryRepository;
import com.demo.book.service.BookService;
import com.demo.book.service.FileHandlerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@org.springframework.stereotype.Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BookServiceImpl implements BookService {
    private final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CloudinaryFileUpload imageUpload;
    @Autowired
    private FileHandlerFactory fileHandlerFactory;
    @Autowired
    private ServiceAbstractFactory factory;

    @Transactional
    @Override
    public Book createBook(MultipartFile file, String model) throws IOException {
        long start = System.currentTimeMillis();
        BookDto bookDto = new ObjectMapper().readValue(model, BookDto.class);
        Book book;

        if (file != null) {
            String url = fileHandlerFactory.createFileUpload().uploadFile(file);
            bookDto.setImageUrl(url);
        }
        book = factory.createIBook().createBook(bookDto);
        return book;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(long id) {
        Optional<Book> optional = factory.createIBook().findBookById(id);
        return optional.orElse(null);
    }
    @Transactional
    @Override
    public Book update(MultipartFile file, String model) throws IOException {
        Book book = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(model, Book.class);
        Book updatedBook;
        if(file == null ) {
            updatedBook = factory.createIBook().updateBook(book);
        } else {
            String url = fileHandlerFactory.createFileUpload().uploadFile(file);
            book.setImageUrl(url);
            updatedBook = factory.createIBook().updateBook(book);
        }
        return updatedBook;
    }

    @Override
    public List<Book> findByCategory(String type){
        return bookRepository.findByCategory(type);
    }

    @Transactional
    @Override
    public void delete(long id) {
        factory.createIBook().deleteBook(id);
    }



}
