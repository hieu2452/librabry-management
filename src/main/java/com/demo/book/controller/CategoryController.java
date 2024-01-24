package com.demo.book.controller;

import com.demo.book.dao.impl.CategoryDAO;
import com.demo.book.entity.Category;
import com.demo.book.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    private final CategoryDAO categoryDAO = CategoryDAO.getInstance();
    @GetMapping("get")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }
    @GetMapping("get/name/{name}")
    public ResponseEntity<?> getCategories(String name) {
        return ResponseEntity.ok(categoryService.findByName(name));
    }

    @PostMapping("create")
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryDAO.save(category).get());
    }
}
