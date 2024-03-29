package com.demo.book.repository;

import com.demo.book.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByCategoryName(String name);

    boolean existsByCategoryName(String name);
}
