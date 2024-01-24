package com.demo.book.service.impl;

import com.demo.book.dto.BookDto;
import com.demo.book.dto.BookFilter;
import com.demo.book.dto.PageableResponse;
import com.demo.book.entity.Book;
import com.demo.book.factory.ServiceAbstractFactory;
import com.demo.book.repository.BookRepository;
import com.demo.book.service.BookService;
import com.demo.book.service.FileHandlerFactory;
import com.demo.book.utils.BookSpecification;
import com.demo.book.utils.PageMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
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
    public PageableResponse<Book> findAll(BookFilter bookFilters) {
        PageRequest pageable = PageRequest.of(bookFilters.getPageNumber(), bookFilters.getPageSize());
        Specification<Book> spec = Specification.where(null);
        if(bookFilters.getCategory()!=null) {
            spec = spec.and(BookSpecification.hasCategory(bookFilters.getCategory()));
        }
        if(bookFilters.getPublisher()!=null) {
            spec = spec.and(BookSpecification.hasPublisher(bookFilters.getPublisher()));
        }

        return PageMapper.mapPageable(bookRepository.findAll(spec,pageable));
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
