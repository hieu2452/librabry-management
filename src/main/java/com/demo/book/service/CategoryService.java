package com.demo.book.service;

import com.demo.book.entity.Category;

import java.util.List;

public interface CategoryService {
    Category findByName(String name);
    List<Category> findAll();
}
