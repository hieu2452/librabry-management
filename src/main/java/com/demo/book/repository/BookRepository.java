package com.demo.book.repository;

import com.demo.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> , JpaSpecificationExecutor<Book> {

    Book findByTitle(String title);

    @Query("SELECT b FROM Book b JOIN b.category c WHERE c.categoryName LIKE ?1")
    List<Book> findByCategory(String type);
    @Modifying
    @Query("delete from Book where id = ?1")
    int deleteById(long id);
}
