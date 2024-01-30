package com.demo.book.repository;

import com.demo.book.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("MEMBER")
public interface MemberRepository extends JpaRepository<Member,Long> {
    @Query("SELECT m FROM Member m JOIN m.libraryCard l WHERE l.id =?1 ")
    Optional<Member> findByLibraryCard(long id);
}


