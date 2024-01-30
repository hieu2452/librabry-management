package com.demo.book.service.impl;

import com.demo.book.domain.dto.BookDto;
import com.demo.book.domain.params.BookFilter;
import com.demo.book.domain.response.PageableResponse;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
        PageRequest pageable = PageRequest.of(bookFilters.getPageNumber(), bookFilters.getPageSize(), Sort.by(Sort.Direction.DESC,"addedDate"));
        Specification<Book> spec = Specification.where(null);
        if(bookFilters.getCategory()!=null && !bookFilters.getCategory().equals("all")) {
            spec = spec.and(BookSpecification.byCategory(bookFilters.getCategory()));
        }
        if(bookFilters.getPublisher()!=null && !bookFilters.getPublisher().equals("all")) {
            spec = spec.and(BookSpecification.byPublisher(bookFilters.getPublisher()));
        }
        if(bookFilters.getLanguage()!=null && !bookFilters.getLanguage().equals("all")) {
            spec = spec.and(BookSpecification.byLanguage(bookFilters.getLanguage()));
        }

        return PageMapper.mapPageable(bookRepository.findAll(spec,pageable));
    }
    @Override
    public PageableResponse<Book> findByKeyword(BookFilter filter) {
        PageRequest pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize(), Sort.by(Sort.Direction.DESC,"addedDate"));

        Specification<Book> spec1 = BookSpecification.byCategory(filter.getKeyword());
        Specification<Book> spec2 = BookSpecification.byPublisher(filter.getKeyword());
        Specification<Book> spec3 = BookSpecification.byPublisher(filter.getKeyword());
        Specification<Book> spec4 = BookSpecification.byAuthor(filter.getKeyword());
        Specification<Book> spec5 = BookSpecification.byLanguage(filter.getKeyword());

        return PageMapper.mapPageable(
                bookRepository.findAll(
                        Specification.where(spec1).or(spec2).or(spec3).or(spec4).or(spec5),
                        pageable));
    }


    @Override
    public Book findById(long id) {
        Optional<Book> optional = factory.createIBook().findBookById(id);
        return optional.orElse(null);
    }
    @Transactional
    @Override
    public Book update(MultipartFile file, String model) throws IOException {
        BookDto book = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(model, BookDto.class);
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
