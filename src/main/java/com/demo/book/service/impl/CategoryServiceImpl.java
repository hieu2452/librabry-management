package com.demo.book.service.impl;

import com.demo.book.entity.Category;
import com.demo.book.entity.Publisher;
import com.demo.book.exception.CategoryNotFoundException;
import com.demo.book.repository.CategoryRepository;
import com.demo.book.repository.PublisherRepository;
import com.demo.book.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Override
    public Category findByName(String name) {
        return categoryRepository.findByCategoryName(name).orElseThrow(CategoryNotFoundException::new);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Publisher> findPublishers() {
        return publisherRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        if(categoryRepository.existsByCategoryName(category.getCategoryName())) throw new IllegalArgumentException();

        return categoryRepository.save(category);
    }
}
