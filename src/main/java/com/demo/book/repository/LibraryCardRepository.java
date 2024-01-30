package com.demo.book.repository;

import com.demo.book.entity.LibraryCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LibraryCardRepository extends JpaRepository<LibraryCard,Long> {
//    @Query("SELECT lc FROM LibraryCard lc JOIN lc.member u WHERE u.id = ?1")
//    LibraryCard findByUserId(long id);
}
