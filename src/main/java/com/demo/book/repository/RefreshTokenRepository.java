package com.demo.book.repository;

import com.demo.book.entity.RefreshToken;
import com.demo.book.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByStaff(Staff staff);
    @Modifying
    int deleteByStaff(Staff staff);
}
