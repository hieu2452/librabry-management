package com.demo.book.controller;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    private BookService bookService;


    @PostMapping(value = "/book/create-book",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createBook(@RequestParam(value = "file",required = false) MultipartFile file, @RequestParam("model") String model) throws IOException {
        bookService.createBook(file,model);
        return ResponseEntity.ok(bookService.createBook(file,model));
    }
    @GetMapping("/book/get-all")
    public ResponseEntity<?> getAllBooks() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/get/book/{id}")
    public ResponseEntity<?> getBookById(@PathVariable long id) {
        Book book = bookService.findById(id);
        if(book!=null)
            return new ResponseEntity<>(book, HttpStatus.OK);
        return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
    }
    @GetMapping("get/book/type/{type}")
    public ResponseEntity<?> getBookByType(@PathVariable String type) {
        return ResponseEntity.ok(bookService.findByType(type));
    }

    @GetMapping("/book/get-type")
    public ResponseEntity<?> getBookTypes() {
        List<BookType> bookTypes = Arrays.asList(BookType.values());
        return ResponseEntity.ok(bookTypes);
    }
}
