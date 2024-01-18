package com.demo.book.repository;

import com.demo.book.entity.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrarianRepository extends JpaRepository<Librarian,Long> {
}
