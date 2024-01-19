package com.demo.book.factory;

import com.demo.book.dto.BookDto;
import com.demo.book.entity.Book;
import com.demo.book.entity.Category;
import com.demo.book.repository.BookRepository;
import com.demo.book.repository.CategoryRepository;
import com.demo.book.service.impl.BookServiceImpl;
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
    @Override
    public Book createBook(BookDto bookDto) {
        List<Category> categories = new ArrayList<>();
        for(String name : bookDto.getCategories()) {
            Category category = categoryRepository.findByCategoryName(name);
            if(category != null) categories.add(category);
        }
        Book book = new Book.Builder(bookDto.getTitle(), bookDto.getPrice(), bookDto.getAuthor(),bookDto.getQuantity())
                .description(bookDto.getDescription())
                .subTitle(bookDto.getSubTitle())
                .categories(categories)
                .language(bookDto.getLanguage())
                .build();
        return bookRepository.save(book);
    }

    @Override
    public Book getByCategory(String category) {
        return null;
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
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
