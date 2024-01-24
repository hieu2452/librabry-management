package com.demo.book.controller;

import com.demo.book.adapter.BookLanguage;
import com.demo.book.dto.BookFilter;
import com.demo.book.entity.Book;
import com.demo.book.entity.enums.BookType;
import com.demo.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/book")
@CrossOrigin
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private BookLanguage bookLanguage;

    @PostMapping(value = "/create-book",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createBook(@RequestParam(value = "file",required = false) MultipartFile file, @RequestParam("model") String model) throws IOException {
        return ResponseEntity.ok(bookService.createBook(file,model));
    }

    @PutMapping(value = "/update-book",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateBook(@RequestParam(value = "file",required = false) MultipartFile file, @RequestParam("model") String model) throws IOException {
        return ResponseEntity.ok(bookService.update(file,model));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getBooks(@ModelAttribute BookFilter bookFilter) {
        return ResponseEntity.ok(bookService.findAll(bookFilter));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBookById(@PathVariable long id) {
        Book book = bookService.findById(id);
        if(book!=null)
            return new ResponseEntity<>(book, HttpStatus.OK);
        return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
    }
    @GetMapping("get/type/{type}")
    public ResponseEntity<?> getBookByType(@PathVariable String type) {
        return ResponseEntity.ok(bookService.findByCategory(type));
    }

    @GetMapping("/get/get-type")
    public ResponseEntity<?> getBookTypes() {
        List<BookType> bookTypes = Arrays.asList(BookType.values());
        return ResponseEntity.ok(bookTypes);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable long id) {
        try {
            bookService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Unable to delete this Book");
        }
    }

    @GetMapping("/get/type-lg/{type}")
    public ResponseEntity<?> getBookByTypeAndLanguage(@PathVariable String type, @RequestParam String lang) {
        return ResponseEntity.ok(bookLanguage.getBookByTypeAndLanguage(type,lang));
    }
}
