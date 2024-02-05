package com.demo.book.controller;

import com.demo.book.adapter.BookLanguage;
import com.demo.book.domain.dto.BookDto;
import com.demo.book.domain.params.BookFilter;
import com.demo.book.entity.Book;
import com.demo.book.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/book")
@CrossOrigin
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookLanguage bookLanguage;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createBook(@Valid @RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.createBook(bookDto),HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateBook(@Valid @RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.update(bookDto));
    }

    @GetMapping("")
    public ResponseEntity<?> getBooks(@ModelAttribute BookFilter bookFilter) {
        return ResponseEntity.ok(bookService.findAll(bookFilter));
    }
    @GetMapping("search")
    public ResponseEntity<?> searchByKeyword(@ModelAttribute BookFilter bookFilter) {
        return ResponseEntity.ok(bookService.findByKeyword(bookFilter));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable long id) {
        Book book = bookService.findById(id);
        return new ResponseEntity<>(bookService.findById(id), HttpStatus.OK);
    }
//    @GetMapping("get/type/{type}")
//    public ResponseEntity<?> getBookByType(@PathVariable String type) {
//        return ResponseEntity.ok(bookService.findByCategory(type));
//    }
//
//    @GetMapping("/get/get-type")
//    public ResponseEntity<?> getBookTypes() {
//        List<BookType> bookTypes = Arrays.asList(BookType.values());
//        return ResponseEntity.ok(bookTypes);
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/get/type-lg/{type}")
//    public ResponseEntity<?> getBookByTypeAndLanguage(@PathVariable String type, @RequestParam String lang) {
//        return ResponseEntity.ok(bookLanguage.getBookByTypeAndLanguage(type,lang));
//    }
}
