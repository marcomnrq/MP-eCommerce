package com.marcomnrq.ecommerce.resource.authentication;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RefreshTokenRequest {
    @NotBlank
    private String refreshToken;
}
