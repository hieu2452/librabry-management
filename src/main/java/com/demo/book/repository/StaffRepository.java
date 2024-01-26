package com.demo.book.repository;

import com.demo.book.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("STAFF")
public interface StaffRepository extends JpaRepository<Staff,Long> {
    Staff findByUsername(String username);
    boolean existsByUsername(String username);
}
