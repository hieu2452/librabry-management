package com.demo.book.UnitTest.service;

import com.demo.book.domain.dto.BookDto;
import com.demo.book.domain.params.BookFilter;
import com.demo.book.domain.response.PageableResponse;
import com.demo.book.entity.Book;
import com.demo.book.entity.Category;
import com.demo.book.entity.Publisher;
import com.demo.book.exception.BookNotFoundException;
import com.demo.book.exception.CategoryNotFoundException;
import com.demo.book.exception.PublisherNotFoundException;
import com.demo.book.factory.ServiceAbstractFactory;
import com.demo.book.repository.BookRepository;
import com.demo.book.repository.CategoryRepository;
import com.demo.book.repository.PublisherRepository;
import com.demo.book.service.FileHandlerFactory;
import com.demo.book.service.impl.BookServiceImpl;
import com.demo.book.utils.BookSpecification;

import com.demo.book.utils.MathUtil;
import org.junit.jupiter.api.BeforeEach;
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
    private Specification<Book> spec;
    private BookFilter bookFilters;
    private List<Book> books;
    private PageRequest pageable;
    @BeforeEach
    public void init() {
        spec = Specification.where(null);

        bookFilters = new BookFilter();
        bookFilters.setPageNumber(0);
        bookFilters.setPageSize(5);

        Category category1 = new Category("Lecture");
        Category category3 = new Category("Math");
        pageable = PageRequest.of(bookFilters.getPageNumber(), bookFilters.getPageSize(), Sort.by(Sort.Direction.DESC,"addedDate"));
        books = Arrays.asList(new Book.Builder("book1", "book1", 5).category(category1).build(),
                new Book.Builder("book3", "book3", 7).category(category3).build());
    }

    @Test
    public void findAll_ReturnListOfBooks() {
        Pageable pageable = PageRequest.of(bookFilters.getPageNumber(), bookFilters.getPageSize(),Sort.by(Sort.Direction.DESC,"addedDate"));

        List<Book> books = this.books;
        Page<Book> page = new PageImpl<>(books, pageable, books.size());
        when(bookRepository.findAll(eq(spec), eq(pageable))).thenReturn(page);

        PageableResponse<Book> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(page.getContent());
        pageableResponse.setTotalItems(page.getTotalElements());
        pageableResponse.setTotalPages(page.getTotalPages());
        pageableResponse.setCurrentPage(page.getPageable().getPageNumber());

        PageableResponse<Book> result = bookService.findAll(bookFilters);

        assertEquals(books.size(), result.getContent().size());
        assertEquals(page.getTotalPages(), result.getTotalPages());
    }

    @Test
    public void findByKeyword_ReturnListOfBooks() {
        BookFilter filter = new BookFilter();
        filter.setKeyword("searchKeyword");

        List<Book> books = this.books;
        Page<Book> page = new PageImpl<>(books, pageable, books.size());
        when(bookRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(page);

        PageableResponse<Book> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(page.getContent());
        pageableResponse.setTotalItems(page.getTotalElements());
        pageableResponse.setTotalPages(page.getTotalPages());
        pageableResponse.setCurrentPage(page.getPageable().getPageNumber());

        PageableResponse<Book> result = bookService.findByKeyword(filter);


        assertEquals(books.size(), result.getContent().size());
        assertEquals(page.getTotalPages(), result.getTotalPages());
    }

    @Test
    public void findAllWithCategoryFilter_ReturnListOfBooks() {
        bookFilters.setCategory("Math");
        spec = spec.and(BookSpecification.byCategory(bookFilters.getCategory()));
        Pageable pageable = PageRequest.of(bookFilters.getPageNumber(), bookFilters.getPageSize(),Sort.by(Sort.Direction.DESC,"addedDate"));

        List<Book> books = this.books;
        Page<Book> page = new PageImpl<>(books, pageable, books.size());
        when(bookRepository.findAll(any(Specification.class),any(PageRequest.class))).thenReturn(page);

        PageableResponse<Book> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(page.getContent());
        pageableResponse.setTotalItems(page.getTotalElements());
        pageableResponse.setTotalPages(page.getTotalPages());
        pageableResponse.setCurrentPage(page.getPageable().getPageNumber());

        PageableResponse<Book> result = bookService.findAll(bookFilters);

        assertEquals(books.size(), result.getContent().size());
        assertEquals(page.getTotalPages(), result.getTotalPages());
    }

    @Test
    public void whenGetValidId_returnBook() {
        Book expected = new Book.Builder("book1", "book1", 5).id(22).build();

        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(expected));

        Book book = bookService.findById(22);

        assertEquals(expected.getId(),book.getId());

        verify(bookRepository).findById(22L);
    }

    @Test
    public void whenGetInValidId_returnNull() {
        long inValidId = 25L;

        when(bookRepository.findById(any(Long.class))).thenThrow(BookNotFoundException.class);

        assertThrowsExactly(BookNotFoundException.class,() -> bookService.findById(inValidId));
    }

    @Test
    public void createBookNonExistCategory_returnNotFound() {
        BookDto bookDto = new BookDto();
        bookDto.setId(1);
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
    public void createBookNonExistPublisher_returnNotFound() {
        BookDto bookDto = new BookDto();
        bookDto.setId(1);
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
        assertThrowsExactly(PublisherNotFoundException.class,() ->bookService.update(bookDto));
    }

    @Test
    public void updateBook_SuccessfulUpdate_ReturnsUpdatedBook () {
        BookDto bookDto = new BookDto();
        bookDto.setId(1);
        bookDto.setTitle("silent hill");
        bookDto.setAuthor("logan");
        bookDto.setCategory("lecture");
        bookDto.setLanguage("vietnamese");
        bookDto.setPublisher("kim dong");

        Category category = new Category("lecture");
        Publisher publisher = new Publisher("vietnamese");
        when(categoryRepository.findByCategoryName(any(String.class))).thenReturn(Optional.of(category));
        when(publisherRepository.findByName(any(String.class))).thenReturn(Optional.of(publisher));

        Book updatedBook = new Book.Builder(bookDto.getTitle(),bookDto.getAuthor(),bookDto.getQuantity())
                .id(bookDto.getId())
                .description(bookDto.getDescription())
                .category(category)
                .publisher(publisher)
                .language(bookDto.getLanguage())
                .build();

        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);
        Book result = bookService.update(bookDto);

        assertEquals(bookDto.getId(),result.getId());
    }

    @Test
    public void updateBook_NullBookId_ThrowException () {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("silent hill");
        bookDto.setAuthor("logan");
        bookDto.setCategory("lecture");
        bookDto.setLanguage("vietnamese");
        bookDto.setPublisher("kim dong");

        assertThrowsExactly(IllegalArgumentException.class,() -> bookService.update(bookDto));
    }


    @Test
    public void createBook_SuccessfulCreate_ReturnsCreatedBook () {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("silent hill");
        bookDto.setAuthor("logan");
        bookDto.setCategory("lecture");
        bookDto.setLanguage("vietnamese");
        bookDto.setPublisher("kim dong");

        Category category = new Category("lecture");
        Publisher publisher = new Publisher("vietnamese");
        when(categoryRepository.findByCategoryName(any(String.class))).thenReturn(Optional.of(category));
        when(publisherRepository.findByName(any(String.class))).thenReturn(Optional.of(publisher));

        Book createdBook = new Book.Builder(bookDto.getTitle(),bookDto.getAuthor(),bookDto.getQuantity())
                .id(bookDto.getId())
                .description(bookDto.getDescription())
                .category(category)
                .publisher(publisher)
                .language(bookDto.getLanguage())
                .build();

        when(bookRepository.save(any(Book.class))).thenReturn(createdBook);
        Book result = bookService.createBook(bookDto);

        assertEquals(bookDto.getId(),result.getId());
    }

    @Test
    public void deleteBook_BookIdNull_ThrowException() {
        long bookId = 1L;

        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrowsExactly(BookNotFoundException.class,() -> bookService.delete(bookId));

    }

    @Test
    public void deleteBook_DeleteSuccessful_ThrowException() {
        Book book= new Book.Builder("book1", "book1", 5).id(1L).build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.delete(1L);

        verify(bookRepository,times(1)).delete(book);

    }




}
