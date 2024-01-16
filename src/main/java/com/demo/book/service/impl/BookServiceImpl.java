package com.demo.book.service.impl;

import com.demo.book.entity.Book;
import com.demo.book.repository.BookRepository;
import com.demo.book.service.BookService;
import com.demo.book.service.FileHandlerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BookServiceImpl implements BookService {
    private final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CloudinaryFileUpload imageUpload;
    @Autowired
    private FileHandlerFactory fileHandlerFactory;
    @Transactional
    @Override
    public Book createBook(MultipartFile file, String model) throws IOException {
        long start = System.currentTimeMillis();
        Book book = new ObjectMapper().readValue(model, Book.class);
        Book newBook;
        if(file == null ) {
            newBook = new Book.Builder(book.getTitle(),book.getPrice())
                    .subTitle(book.getSubTitle())
                    .description(book.getDescription())
                    .build();
        } else {
            String url = fileHandlerFactory.createFileUpload().uploadFile(file);
            newBook = new Book.Builder(book.getTitle(),book.getPrice())
                    .subTitle(book.getSubTitle())
                    .description(book.getDescription())
                    .imageUrl(url)
                    .build();
        }
        bookRepository.save(newBook);
        long end = System.currentTimeMillis();
        log.info("time took to save entity " + (end - start));
        return newBook;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(long id) {
        return bookRepository.findById(id).orElse(null);
    }

}
