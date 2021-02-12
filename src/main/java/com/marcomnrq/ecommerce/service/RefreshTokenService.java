package com.marcomnrq.ecommerce.service;

import com.marcomnrq.ecommerce.domain.model.RefreshToken;
import com.marcomnrq.ecommerce.domain.repository.RefreshTokenRepository;
import com.marcomnrq.ecommerce.exception.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken() {
        // Generating a new refresh token
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());

        // Storing it in the database
        return refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token).orElseThrow(() -> new CustomException("Invalid refresh token"));
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
