package com.demo.book.service;

import com.demo.book.domain.dto.BookDto;
import com.demo.book.domain.params.BookFilter;
import com.demo.book.domain.response.PageableResponse;
import com.demo.book.entity.Book;
import com.demo.book.entity.Category;
import com.demo.book.exception.BookNotFoundException;
import com.demo.book.exception.CategoryNotFoundException;
import com.demo.book.exception.PublisherNotFoundException;
import com.demo.book.factory.ServiceAbstractFactory;
import com.demo.book.repository.BookRepository;
import com.demo.book.repository.CategoryRepository;
import com.demo.book.repository.PublisherRepository;
import com.demo.book.service.impl.BookServiceImpl;
import com.demo.book.utils.BookSpecification;
import javafx.beans.binding.When;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private PublisherRepository publisherRepository;
    @Mock
    private FileHandlerFactory fileHandlerFactory;
    @Mock
    private ServiceAbstractFactory factory;

    @InjectMocks
    private BookServiceImpl bookService;

//    @Test
//    public void findAll_WithCategoryFilter_ShouldReturnFilteredBook() {
//        BookFilter bookFilters = new BookFilter();
//        bookFilters.setPageNumber(0);
//        bookFilters.setPageSize(5);
//        bookFilters.setCategory("lecture");
//
//        Category category1 = new Category("lecture");
//        Category category3 = new Category("lecture");
//
//        List<Book> testData;
//        testData = Arrays.asList(new Book.Builder("book1", "book1", 5).category(category1).build(),
//                new Book.Builder("book3", "book3", 7).category(category3).build());
//
//        PageableResponse<Book> expected = new PageableResponse<>();
//        expected.setContent(testData);
//        expected.setTotalPages(1);
//        expected.setTotalItems(3);
//        PageRequest pageable = PageRequest.of(bookFilters.getPageNumber(), bookFilters.getPageSize(), Sort.by(Sort.Direction.DESC,"addedDate"));
//        Specification<Book> spec = Specification.where(null);
//        spec = spec.and(BookSpecification.byCategory(eq(bookFilters.getCategory())));
//        Page<Book> page = new PageImpl<>(testData);
//        when(bookRepository.findAll(spec,eq(pageable)))
//                .thenReturn(page);
//
//        PageableResponse<Book> result = bookService.findAll(bookFilters);
//
//        assertEquals(expected.getContent().size(),result.getContent().size());
//
//        verify(bookRepository, times(1)).findAll(spec,eq(pageable));
//    }

    @Test
    public void whenGetValidId_returnBook() {
        Book expected = new Book.Builder("book1", "book1", 5).id(22).build();

        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));

        Book book = bookService.findById(22);

        assertEquals(expected.getTitle(),book.getTitle());

        verify(bookRepository).findById(22L);
    }

    @Test()
    public void whenGetInValidId_returnNull() {
        long inValidId = 25L;

        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(null));

        assertThrowsExactly(BookNotFoundException.class,() -> bookService.findById(inValidId));
    }

    @Test
    public void whenGetInvalidCategory_returnNotFound() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("silent hill");
        bookDto.setAuthor("logan");
        bookDto.setCategory("lecture");
        bookDto.setLanguage("vietnamese");
        bookDto.setPublisher("kim dong");

        when(categoryRepository.findByCategoryName(any(String.class))).thenReturn(Optional.empty());

        assertThrowsExactly(CategoryNotFoundException.class,() ->bookService.createBook(bookDto));
        assertThrowsExactly(CategoryNotFoundException.class,() ->bookService.update(bookDto));
    }

    @Test
    public void whenGetInvalidPublisher_returnNotFound() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("silent hill");
        bookDto.setAuthor("logan");
        bookDto.setCategory("lecture");
        bookDto.setLanguage("vietnamese");
        bookDto.setPublisher("kim dong");

        Category category = new Category();
        category.setCategoryName("lecture");

        when(publisherRepository.findByName(any(String.class))).thenReturn(Optional.empty());
        when(categoryRepository.findByCategoryName(any(String.class))).thenReturn(Optional.of(category));

        assertThrowsExactly(PublisherNotFoundException.class,() ->bookService.createBook(bookDto));
//        assertThrowsExactly(CategoryNotFoundException.class,() ->bookService.update(bookDto));
    }
}
