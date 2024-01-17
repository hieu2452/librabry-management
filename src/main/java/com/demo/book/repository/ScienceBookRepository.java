package com.demo.book.repository;

import com.demo.book.entity.ScienceBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScienceBookRepository extends JpaRepository<ScienceBook,Long> {
}
