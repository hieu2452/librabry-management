package com.demo.book.repository;

import com.demo.book.entity.LecturerBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturerBookRepository extends JpaRepository<LecturerBook,Long> {
//    LecturerBook find(String type);
}
