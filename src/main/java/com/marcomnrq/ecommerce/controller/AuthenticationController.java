package com.marcomnrq.ecommerce.controller;

import com.marcomnrq.ecommerce.resource.authentication.AuthenticationResponse;
import com.marcomnrq.ecommerce.resource.authentication.LoginRequest;
import com.marcomnrq.ecommerce.resource.authentication.RefreshTokenRequest;
import com.marcomnrq.ecommerce.resource.authentication.RegistrationRequest;
import com.marcomnrq.ecommerce.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody RegistrationRequest registrationRequest){
        authenticationService.signUp(registrationRequest);
        return new ResponseEntity<>("User registration was succesfull", HttpStatus.OK);
    }

    @PostMapping("/sign-in")
    public AuthenticationResponse signIn(@RequestBody LoginRequest loginRequest){
        return authenticationService.signIn(loginRequest);
    }

    @PostMapping("/refresh-token")
    public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest, Principal principal){
        String email = principal.getName();
        return authenticationService.refreshToken(email, refreshTokenRequest);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<String> signOut(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        authenticationService.signOut(refreshTokenRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Refresh token has been deleted");
    }

    /*
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest, Principal principal){
        String username = principal.getName();
        authenticationService.changePassword(username, changePasswordRequest);
        return new ResponseEntity<>("Password changed", HttpStatus.OK);
    }
    */

}