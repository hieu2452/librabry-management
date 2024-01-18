package com.demo.book.repository;

import com.demo.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
//    @Query("SELECT b FROM Book b JOIN b.categories c WHERE c.categoryName = ?1")
//    List<Book> findByType(String type);
    Book findByTitle(String title);
}
