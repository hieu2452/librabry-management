package com.demo.book.repository;

import com.demo.book.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ADMIN")
public interface AdminRepository extends JpaRepository<Admin,Long> {
}
