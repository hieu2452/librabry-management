package com.demo.book.service.impl;

import com.demo.book.entity.RefreshToken;
import com.demo.book.entity.Staff;
import com.demo.book.exception.TokenRefreshException;
import com.demo.book.repository.RefreshTokenRepository;
import com.demo.book.repository.StaffRepository;
import com.demo.book.repository.UserRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
@Service
public class RefreshTokenService {
    @Value("${spring.refresh-token.time-to-live}")
    private Long refreshTokenTTL;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private StaffRepository staffRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Staff staff) {
        RefreshToken refreshToken = new RefreshToken();
        Optional<RefreshToken> optional = refreshTokenRepository.findByStaff(staff);

        if(optional.isPresent()) {
            return optional.get();
        }

        refreshToken.setStaff(staffRepository.findById(staff.getId()).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenTTL));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByStaff(staffRepository.findById(userId).get());
    }
}
