package com.demo.book.service;

import com.demo.book.entity.Category;
import com.demo.book.entity.Publisher;

import java.util.List;

public interface CategoryService {
    Category findByName(String name);
    List<Category> findAll();
    List<Publisher> findPublishers();
}
