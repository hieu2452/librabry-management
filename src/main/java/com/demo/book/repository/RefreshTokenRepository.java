package com.demo.book.repository;

import com.demo.book.entity.RefreshToken;
import com.demo.book.entity.Staff;
import com.demo.book.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);
    @Modifying
    int deleteByStaff(Staff staff);
}
