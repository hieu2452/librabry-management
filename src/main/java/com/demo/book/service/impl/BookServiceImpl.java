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
import com.demo.book.repository.BookRepository;
import com.demo.book.repository.CategoryRepository;
import com.demo.book.repository.PublisherRepository;
import com.demo.book.service.BookService;
import com.demo.book.utils.BookSpecification;
import com.demo.book.utils.PageMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

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


    @Transactional
    @Override
    public Book createBook(BookDto bookDto){

        Category category = categoryRepository.findByCategoryName(bookDto.getCategory()).orElseThrow(CategoryNotFoundException::new);
        Publisher publisher = publisherRepository.findByName(bookDto.getPublisher()).orElseThrow(PublisherNotFoundException::new);
        Book book = new Book.Builder(bookDto.getTitle(),bookDto.getAuthor(),bookDto.getQuantity())
                .description(bookDto.getDescription())
                .category(category)
                .publisher(publisher)
                .language(bookDto.getLanguage())
                .build();
        return bookRepository.save(book);

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
        Optional<Book> optional = bookRepository.findById(id);
        return optional.orElseThrow(() -> new BookNotFoundException(id));
    }
    @Transactional
    @Override
    public Book update(BookDto bookDto) {
        if(bookDto.getId() == 0) throw new IllegalArgumentException("Book id can not be null");
        Category category = categoryRepository.findByCategoryName(bookDto.getCategory()).orElseThrow(CategoryNotFoundException::new);
        Publisher publisher = publisherRepository.findByName(bookDto.getPublisher()).orElseThrow(PublisherNotFoundException::new);
        Book updatedBook = new Book.Builder(bookDto.getTitle(),bookDto.getAuthor(),bookDto.getQuantity())
                .id(bookDto.getId())
                .description(bookDto.getDescription())
                .category(category)
                .publisher(publisher)
                .language(bookDto.getLanguage())
                .build();
        return bookRepository.save(updatedBook);
    }

//    @Override
//    public List<Book> findByCategory(String type){
//        return bookRepository.findByCategory(type);
//    }

    @Transactional
    @Override
    public void delete(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.delete(book);
    }

}
