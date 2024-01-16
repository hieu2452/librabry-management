package com.demo.book.controller;

import com.demo.book.entity.User;
import com.demo.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
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
        bookService.createbook(file,model);
        return ResponseEntity.ok("Success");
    }
    @GetMapping("/get-all")
    public List<User> getAll() {
        return null;
    }
}
