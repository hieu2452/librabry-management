package com.demo.book.controller;

import com.demo.book.entity.Book;
import com.demo.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookService bookService;


    @PostMapping(value = "/create-book",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createBook(@RequestParam(value = "file",required = false) MultipartFile file, @RequestParam("model") String model) throws IOException {
        bookService.createBook(file,model);
        return ResponseEntity.ok(bookService.createBook(file,model));
    }
    @GetMapping("/get-all")
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
}
