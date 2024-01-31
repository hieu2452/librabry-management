package com.demo.book.service;

import com.demo.book.domain.dto.BookDto;
import com.demo.book.domain.params.BookFilter;
import com.demo.book.domain.response.PageableResponse;
import com.demo.book.entity.Book;
import com.demo.book.service.impl.BookServiceImpl;
import com.demo.book.utils.PageMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest
public class BookServiceImplDaoTest {

    @Autowired
    private BookServiceImpl bookService;
    @Test
    public void findAllFilteredBookByCategory() {
        BookFilter bookFilter = new BookFilter();
        bookFilter.setCategory("lecture");

        PageableResponse<Book> response = bookService.findAll(bookFilter);
        boolean match = true;
        for(Book book : response.getContent()){
            if(!book.getCategory().getCategoryName().equalsIgnoreCase(bookFilter.getCategory())){
                match = false;
            }
        }
        assertTrue(match);
    }

    @Test
    public void findAllFilteredBookByPublisher() {
        BookFilter bookFilter = new BookFilter();
        bookFilter.setPublisher("Kim");

        PageableResponse<Book> response = bookService.findAll(bookFilter);
        boolean match = true;
        for(Book book : response.getContent()){
            if(!book.getPublisher().getName().contains(bookFilter.getPublisher())){
                match = false;
            }
        }
        assertTrue(match);
    }


    @Test
    public void findAllFilteredBookIfCategoryNotExist() {
        BookFilter bookFilter = new BookFilter();
        bookFilter.setCategory("hay");

        PageableResponse<Book> response = bookService.findAll(bookFilter);

        assertTrue(response.getContent().isEmpty());
    }

    @Test
    public void findAllFilteredBookIfPublisherNotExist() {
        BookFilter bookFilter = new BookFilter();
        bookFilter.setPublisher("hay");

        PageableResponse<Book> response = bookService.findAll(bookFilter);

        assertTrue(response.getContent().isEmpty());
    }
}
