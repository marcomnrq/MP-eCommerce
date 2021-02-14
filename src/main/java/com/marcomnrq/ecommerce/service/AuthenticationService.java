package com.marcomnrq.ecommerce.service;

import com.marcomnrq.ecommerce.domain.model.Role;
import com.marcomnrq.ecommerce.domain.model.User;
import com.marcomnrq.ecommerce.domain.repository.RoleRepository;
import com.marcomnrq.ecommerce.domain.repository.UserRepository;
import com.marcomnrq.ecommerce.resource.authentication.AuthenticationResponse;
import com.marcomnrq.ecommerce.resource.authentication.LoginRequest;
import com.marcomnrq.ecommerce.resource.authentication.RefreshTokenRequest;
import com.marcomnrq.ecommerce.resource.authentication.RegistrationRequest;
import com.marcomnrq.ecommerce.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final RefreshTokenService refreshTokenService;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    @Transactional
    public void signUp(RegistrationRequest registrationRequest) {

        // Creating a new user based of registration dto
        User user = new User();
        user.setEmail(registrationRequest.getEmail());
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        user.setEnabled(true);


        // Saving the new user to the database
        userRepository.save(user);

    }

    public AuthenticationResponse signIn(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(
                token,
                refreshTokenService.generateRefreshToken(loginRequest.getEmail()).getToken(),
                Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()),
                loginRequest.getEmail()
        );
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        // Refresh the token
        refreshTokenService.validateRefreshToken(refreshTokenRequest);
        String token = jwtProvider.generateTokenWithEmail(refreshTokenRequest.getEmail());
        return new AuthenticationResponse(
                token,
                refreshTokenRequest.getRefreshToken(),
                Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()),
                refreshTokenRequest.getEmail());
    }

    public void signOut(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest);
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
    }

}
