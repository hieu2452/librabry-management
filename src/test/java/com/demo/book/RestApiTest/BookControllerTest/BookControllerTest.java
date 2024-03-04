package com.demo.book.RestApiTest.BookControllerTest;


import com.demo.book.controller.BookController;
import com.demo.book.domain.dto.BookDto;
import com.demo.book.domain.params.BookFilter;
import com.demo.book.domain.response.PageableResponse;
import com.demo.book.entity.Book;
import com.demo.book.entity.Category;
import com.demo.book.entity.Publisher;
import com.demo.book.exception.BookNotFoundException;
import com.demo.book.helper.BookMappingHelper;
import com.demo.book.service.impl.BookServiceImpl;
import com.demo.book.utils.BookExcelImporter;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(value = BookController.class,excludeAutoConfiguration = SecurityAutoConfiguration.class)
//@Import(ControllerAdviser.class)
public class BookControllerTest {
    @MockBean
    private BookServiceImpl bookService;
    @MockBean
    private BookExcelImporter bookExcelImporter;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    private Book book;

    @BeforeEach
    public void init() {
        book = new Book.Builder("test","author",4).id(1)
                .language("Vietnamese")
                .category(new Category(1,"math"))
                .publisher(new Publisher(1,"NXB")).build();
    }

    @Test
    public void shouldReturnBook() throws Exception {
        long bookId = 1L;
        book = new Book.Builder("test","author",4).id(bookId)
                .language("Vietnamese")
                .category(new Category(1,"math"))
                .publisher(new Publisher(1,"NXB")).build();

        when(bookService.findById(bookId)).thenReturn(BookMappingHelper.map(book));

        mockMvc.perform(get("/api/book/{id}",bookId)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookId))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.quantity").value(book.getQuantity()))
                .andExpect(jsonPath("$.category").value(book.getCategory()))
                .andExpect(jsonPath("$.publisher").value(book.getPublisher()))
                .andDo(print());

    }

    @Test
    public void servletContext_thenReturnBookController() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        assertNotNull(servletContext);
        assertInstanceOf(MockServletContext.class, servletContext);
        assertNotNull(webApplicationContext.getBean("bookController"));
    }

    @Test
    public void whenInvalidId_ShouldReturnNOT_FOUND() throws Exception {
        long bookId = 1L;
        book = new Book.Builder("test","author",4).id(bookId)
                .language("Vietnamese")
                .category(new Category(1,"math"))
                .publisher(new Publisher(1,"NXB")).build();

        when(bookService.findById(any(Long.class))).thenThrow(BookNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/book/{id}",bookId)).andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void shouldCreateBook() throws Exception {

        BookDto bookDto = new BookDto();
        bookDto.setTitle("test");
        bookDto.setAuthor("author");
        bookDto.setLanguage("Vietnamese");
        bookDto.setCategory("math");
        bookDto.setPublisher("nxb");
        bookDto.setQuantity(4);

        when(bookService.createBook(any(BookDto.class))).thenReturn(bookDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/book/create")
                .content(asJsonString(bookDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }
    @Test
    public void shouldReturnBooksWithFilter() throws Exception {
        BookFilter bookFilter = new BookFilter();
        bookFilter.setPageNumber(0);
        bookFilter.setPublisher("nxb");
        bookFilter.setCategory("math");
        bookFilter.setPageSize(2);
        bookFilter.setLanguage("Vietnamese");
        List<Book> books = Arrays.asList(
                book = new Book.Builder("test1","author1",4).id(1)
                        .language("Vietnamese")
                        .category(new Category(1,"math"))
                        .publisher(new Publisher(1,"NXB")).build(),
                book = new Book.Builder("test2","author2",4).id(2)
                        .language("Vietnamese")
                        .category(new Category(1,"math"))
                        .publisher(new Publisher(1,"NXB")).build(),
                book = new Book.Builder("test3","author3",4).id(3)
                        .language("Vietnamese")
                        .category(new Category(1,"math"))
                        .publisher(new Publisher(1,"NXB")).build()
        );
        PageableResponse<BookDto> response = new PageableResponse<>();
        response.setContent(books.stream().map(BookMappingHelper::map).collect(Collectors.toList()));
        response.setTotalItems(books.size());
        response.setTotalPages(books.size()/bookFilter.getPageSize());

        when(bookService.findAll(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/book")
                .param("category",bookFilter.getCategory())
                .param("publisher",bookFilter.getPublisher())
                .param("language",bookFilter.getLanguage())
                .param("pageNumber",String.valueOf(bookFilter.getPageNumber()))
                .param("pageSize",String.valueOf(bookFilter.getPageSize())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").value(response.getContent().size()))
                .andDo(print());
    }
    @Test
    public void shouldReturnBooksByKeyword() throws Exception {
        BookFilter bookFilter = new BookFilter();
        bookFilter.setPageNumber(0);
        bookFilter.setPageSize(2);
        bookFilter.setKeyword("test");
        List<Book> books = Arrays.asList(
                book = new Book.Builder("test1","author1",4).id(1)
                        .language("Vietnamese")
                        .category(new Category(1,"math"))
                        .publisher(new Publisher(1,"NXB")).build(),
                book = new Book.Builder("test2","author2",4).id(2)
                        .language("Vietnamese")
                        .category(new Category(1,"math"))
                        .publisher(new Publisher(1,"NXB")).build(),
                book = new Book.Builder("test3","author3",4).id(3)
                        .language("Vietnamese")
                        .category(new Category(1,"math"))
                        .publisher(new Publisher(1,"NXB")).build()
        );
        PageableResponse<BookDto> response = new PageableResponse<>();
        response.setContent(books.stream().map(BookMappingHelper::map).collect(Collectors.toList()));
        response.setTotalItems(books.size());
        response.setTotalPages(books.size()/bookFilter.getPageSize());

        when(bookService.findByKeyword(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/book/search")
                        .param("search",bookFilter.getKeyword()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems").value(response.getContent().size()))
                .andDo(print());
    }
    @Test
    public void deleteBook() throws Exception {
        long bookId = 1 ;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/book/delete/{id}",bookId))
                .andExpect(status().isNoContent())
                .andDo(print());
        verify(bookService,times(1)).delete(bookId);
    }


    @Test
    public void deleteNonExistBook_ThrowException() throws Exception {
        long bookId = 1 ;

        doThrow(new BookNotFoundException(bookId)).when(bookService).delete(bookId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/book/delete/{id}",bookId))
                .andExpect(status().isNotFound())
                .andDo(print());
        verify(bookService,times(1)).delete(bookId);
    }
    @Test
    public void shouldUpdateBook() throws Exception {

        BookDto bookDto = new BookDto();
        bookDto.setTitle("test1");
        bookDto.setAuthor("author");
        bookDto.setLanguage("Vietnamese");
        bookDto.setCategory("math");
        bookDto.setPublisher("nxb");
        bookDto.setQuantity(4);

        when(bookService.update(any(BookDto.class))).thenReturn(bookDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/book/update")
                        .content(asJsonString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
