package com.demo.book.repository;

import com.demo.book.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRole(String role);
}
