package com.marcomnrq.ecommerce.service;

import com.marcomnrq.ecommerce.domain.model.User;
import com.marcomnrq.ecommerce.domain.repository.UserRepository;
import com.marcomnrq.ecommerce.exception.CustomException;
import com.marcomnrq.ecommerce.resource.UserResource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByEmail(String email){
        // Getting the user
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElseThrow(() -> new CustomException("User not found with email: " + email));

        return user;
    }

    public User updateUser(String email, UserResource userRequest){
        // Getting the user
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElseThrow(() -> new CustomException("User not found with email: " + email));

        // Updating the user and storing it
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());

        return userRepository.save(user);
    }
}
