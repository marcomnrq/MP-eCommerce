package com.marcomnrq.ecommerce.controller;

import com.marcomnrq.ecommerce.domain.model.User;
import com.marcomnrq.ecommerce.resource.UserResource;
import com.marcomnrq.ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final ModelMapper modelMapper;

    private final UserService userService;

    @GetMapping
    public UserResource getUserByEmail(Principal principal) {
        String email = principal.getName();
        return convertToResource(userService.getUserByEmail(email));
    }

    @PutMapping
    public UserResource updateUser(@Valid @RequestBody UserResource userResource, Principal principal) {
        String email = principal.getName();
        return convertToResource(userService.updateUser(email, userResource));
    }

    // Model Mapper
    private UserResource convertToResource(User entity) {
        return modelMapper.map(entity, UserResource.class);
    }
}
