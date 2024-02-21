package com.demo.book.controller;

import com.demo.book.entity.Category;
import com.demo.book.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("get")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }
    @GetMapping("get-publisher")
    public ResponseEntity<?> getPublisher() {
        return ResponseEntity.ok(categoryService.findPublishers());
    }
    @GetMapping("get/name/{name}")
    public ResponseEntity<?> getCategories(String name) {
        return ResponseEntity.ok(categoryService.findByName(name));
    }


    @PostMapping("create")
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        return new ResponseEntity<>(categoryService.save(category), HttpStatus.CREATED);
    }
}
