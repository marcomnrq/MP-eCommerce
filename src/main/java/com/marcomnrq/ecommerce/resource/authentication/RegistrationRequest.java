package com.marcomnrq.ecommerce.resource.authentication;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class RegistrationRequest {
    private String email;

    @Size(max = 20, min = 6)
    private String password;

    private String firstName;
    private String lastName;
}
