package com.demo.book.repository;

import com.demo.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

    Book findByTitle(String title);

    @Query("SELECT b FROM Book b JOIN b.category c WHERE c.categoryName LIKE ?1")
    List<Book> findByCategory(String type);
}
