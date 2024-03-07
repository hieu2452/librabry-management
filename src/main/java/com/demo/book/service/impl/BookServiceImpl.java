package com.demo.book.service.impl;

import com.demo.book.domain.dto.BookDto;
import com.demo.book.domain.params.BookFilter;
import com.demo.book.domain.response.PageableResponse;
import com.demo.book.entity.Book;
import com.demo.book.entity.Category;
import com.demo.book.entity.Publisher;
import com.demo.book.exception.BookNotFoundException;
import com.demo.book.exception.CategoryNotFoundException;
import com.demo.book.exception.PublisherNotFoundException;
import com.demo.book.helper.BookMappingHelper;
import com.demo.book.repository.BookRepository;
import com.demo.book.repository.CategoryRepository;
import com.demo.book.repository.PublisherRepository;
import com.demo.book.service.BookRedisService;
import com.demo.book.service.BookService;
import com.demo.book.utils.BookSpecification;
import com.demo.book.helper.PageMapper;
import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BookServiceImpl implements BookService {
    private final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private BookRedisService bookRedisService;
    @Transactional
    @Override
    public BookDto createBook(BookDto bookDto){

        Category category = categoryRepository.findByCategoryName(bookDto.getCategory()).orElseThrow(CategoryNotFoundException::new);
        Publisher publisher = publisherRepository.findByName(bookDto.getPublisher()).orElseThrow(PublisherNotFoundException::new);
        Book book = new Book.Builder(bookDto.getTitle(),bookDto.getAuthor(),bookDto.getQuantity())
                .description(bookDto.getDescription())
                .category(category)
                .publisher(publisher)
                .language(bookDto.getLanguage())
                .build();
        return BookMappingHelper.map(bookRepository.save(book));

    }


    @Override
    public PageableResponse<BookDto> findAll(BookFilter bookFilters) {
        PageRequest pageable = PageRequest.of(bookFilters.getPageNumber(), bookFilters.getPageSize(), Sort.by(Sort.Direction.DESC,"addedDate"));

        PageableResponse<BookDto> bookResponse = null;

        Specification<Book> spec = Specification.where(null);

        if(bookFilters.getCategory() != null && !bookFilters.getCategory().equals("all")) {
            spec = spec.and(BookSpecification.byCategory(bookFilters.getCategory()));
        }
        if(bookFilters.getPublisher() != null && !bookFilters.getPublisher().equals("all")) {
            spec = spec.and(BookSpecification.byPublisher(bookFilters.getPublisher()));
        }
        if(bookFilters.getLanguage() != null && !bookFilters.getLanguage().equals("all")) {
            spec = spec.and(BookSpecification.byLanguage(bookFilters.getLanguage()));
        }
        PageableResponse<Book> page = PageMapper.mapPageable(bookRepository.findAll(spec,pageable));
        List<BookDto> response = page.getContent().stream().map(BookMappingHelper::map)
                .collect(Collectors.toList());

        bookResponse = new PageableResponse<>();
        bookResponse.setContent(response);
        bookResponse.setTotalPages(page.getTotalPages());
        bookResponse.setCurrentPage(page.getCurrentPage());
        bookResponse.setTotalItems(page.getTotalItems());

        return bookResponse;
    }
    @Override
    public PageableResponse<BookDto> findByKeyword(BookFilter filter) {
        PageRequest pageable = PageRequest.of(filter.getPageNumber(), filter.getPageSize(), Sort.by(Sort.Direction.DESC,"addedDate"));

        Specification<Book> spec = Specification.where(BookSpecification.byCategory(filter.getKeyword()))
                .or(BookSpecification.byPublisher(filter.getKeyword()))
                .or(BookSpecification.byAuthor(filter.getKeyword()))
                .or(BookSpecification.byLanguage(filter.getKeyword()));

        PageableResponse<Book> page = PageMapper.mapPageable(bookRepository
                .findAll(spec,pageable));
        List<BookDto> response = page.getContent().stream().map(BookMappingHelper::map)
                .collect(Collectors.toList());
        PageableResponse<BookDto> pageResponse = new PageableResponse<>();
        pageResponse.setContent(response);
        pageResponse.setTotalPages(page.getTotalPages());
        pageResponse.setCurrentPage(page.getCurrentPage());
        pageResponse.setTotalItems(page.getTotalItems());

        return pageResponse;
    }

    @Override
    public BookDto findById(long id) throws JsonProcessingException {
        BookDto bookDto = bookRedisService.findById(id);

        if(bookDto == null) {
            Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
            bookDto = BookMappingHelper.map(book);
            bookRedisService.save(bookDto);
        }

        return bookDto;
    }

//    @Cacheable(value = "book", key = "#id")
//    @Override
//    public BookDto findById(long id) throws JsonProcessingException {
//
//        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
//        BookDto bookDto = BookMappingHelper.map(book);
//
//        return bookDto;
//    }

    @Transactional
    @Override
    public BookDto update(long id,BookDto bookDto) throws JsonProcessingException {
        if(bookDto.getId() == 0) throw new IllegalArgumentException("Book id can not be null");

        Book oldBook = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        if(!oldBook.getCategory().getCategoryName().equals(bookDto.getAuthor())) {
            Category category = categoryRepository.findByCategoryName(bookDto.getCategory())
                    .orElseThrow(CategoryNotFoundException::new);
            oldBook.setCategory(category);
        }
        if(!oldBook.getPublisher().getName().equals(bookDto.getPublisher())) {
            Publisher publisher = publisherRepository.findByName(bookDto.getPublisher())
                    .orElseThrow(PublisherNotFoundException::new);
            oldBook.setPublisher(publisher);
        }

        oldBook.setDescription(bookDto.getDescription());
        oldBook.setTitle(bookDto.getTitle());
        oldBook.setAuthor(bookDto.getAuthor());
        oldBook.setQuantity(bookDto.getQuantity());
        oldBook.setLanguage(bookDto.getLanguage());

        BookDto updatedBook = BookMappingHelper.map(bookRepository.save(oldBook));
        bookRedisService.save(bookDto);

        return updatedBook;
    }

//    @Override
//    public List<Book> findByCategory(String type){
//        return bookRepository.findByCategory(type);
//    }

    @Transactional
    @Override
    public void delete(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        try {
            int res = bookRepository.deleteById(id);
            if(res > 0) {
                bookRedisService.deleteCache(id);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            log.debug(exception.getMessage());
        }
    }

}
