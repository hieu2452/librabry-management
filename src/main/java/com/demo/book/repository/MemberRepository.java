package com.demo.book.repository;

import com.demo.book.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("MEMBER")
public interface MemberRepository extends JpaRepository<Member,Long> {
}
