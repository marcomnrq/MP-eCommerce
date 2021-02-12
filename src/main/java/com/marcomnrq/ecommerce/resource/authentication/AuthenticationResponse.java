package com.marcomnrq.ecommerce.resource.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private String authenticationToken;
    private String refreshToken;
    private Instant expiresAt;
    private String email;
}
