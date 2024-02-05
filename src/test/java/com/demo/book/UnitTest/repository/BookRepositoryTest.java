package com.demo.book.UnitTest.repository;

import com.demo.book.entity.Book;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@DataJpaTest
public interface BookRepositoryTest extends JpaRepository<Book,Long>, JpaSpecificationExecutor<Book> {

    Book findByTitle(String title);

    @Query("SELECT b FROM Book b JOIN b.category c WHERE c.categoryName LIKE ?1")
    List<Book> findByCategory(String type);
}