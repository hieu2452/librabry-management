package com.demo.book.repository;

import com.demo.book.entity.LectureBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LecturerBookRepository extends JpaRepository<LectureBook,Long> {
//    LecturerBook find(String type);
}
