package com.marcomnrq.ecommerce.service;

import com.marcomnrq.ecommerce.domain.model.RefreshToken;
import com.marcomnrq.ecommerce.domain.model.User;
import com.marcomnrq.ecommerce.domain.repository.RefreshTokenRepository;
import com.marcomnrq.ecommerce.domain.repository.UserRepository;
import com.marcomnrq.ecommerce.exception.CustomException;
import com.marcomnrq.ecommerce.resource.authentication.RefreshTokenRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    public RefreshToken generateRefreshToken(String email) {
        // Accessing the user
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElseThrow(() -> new CustomException("User not found with email: " + email));

        // Generating a new refresh token
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);

        // Storing it in the database
        return refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(RefreshTokenRequest refreshTokenRequest) {
        RefreshToken token = refreshTokenRepository.findByToken(refreshTokenRequest.getRefreshToken())
                .orElseThrow(() -> new CustomException("Invalid refresh token"));
        if(token.getUser().getEmail() != refreshTokenRequest.getEmail()){
            throw new CustomException("Invalid refresh token (email)");
        }
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
