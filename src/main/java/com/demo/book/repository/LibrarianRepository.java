package com.demo.book.repository;

import com.demo.book.entity.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("LIBRARIAN")
public interface LibrarianRepository extends JpaRepository<Librarian,Long> {
}
