package com.demo.book.factory;

import com.demo.book.domain.BookDto;
import com.demo.book.entity.Book;
import com.demo.book.entity.Category;
import com.demo.book.entity.Publisher;
import com.demo.book.repository.BookRepository;
import com.demo.book.repository.CategoryRepository;
import com.demo.book.repository.PublisherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class IBookImpl implements IBook{
    private final Logger log = LoggerFactory.getLogger(IBookImpl.class);
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Override
    public Book createBook(BookDto bookDto) {
        Category category = categoryRepository.findByCategoryName(bookDto.getCategory());
        Publisher publisher = publisherRepository.findByName(bookDto.getPublisher());
        Book book = new Book.Builder(bookDto.getTitle(),bookDto.getAuthor(),bookDto.getQuantity())
                .description(bookDto.getDescription())
                .category(category)
                .publisher(publisher)
                .language(bookDto.getLanguage())
                .build();
        return bookRepository.save(book);
    }

    @Override
    public Book getByCategory(String category) {
        return null;
    }

    @Override
    public Book updateBook(BookDto book) {
        Category category = categoryRepository.findByCategoryName(book.getCategory());
        Publisher publisher = publisherRepository.findByName(book.getPublisher());
        Book updatedBook = new Book.Builder(book.getTitle(),book.getAuthor(),book.getQuantity())
                .id(book.getId())
                .description(book.getDescription())
                .category(category)
                .publisher(publisher)
                .language(book.getLanguage())
                .build();
        return bookRepository.save(updatedBook);
    }

    @Override
    public void deleteBook(long id) {
        try {
            Book book = bookRepository.findById(id).orElse(null);
            assert book != null;
            bookRepository.delete(book);
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new RuntimeException("Unable to delete book");
        }
    }

    @Override
    public Optional<Book> findBookById(long id) {
        return bookRepository.findById(id);
    }

}
