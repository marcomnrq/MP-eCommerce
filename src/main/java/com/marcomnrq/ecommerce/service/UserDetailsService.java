package com.marcomnrq.ecommerce.service;

import com.marcomnrq.ecommerce.domain.model.Role;
import com.marcomnrq.ecommerce.domain.model.User;
import com.marcomnrq.ecommerce.domain.repository.UserRepository;
import com.marcomnrq.ecommerce.exception.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElseThrow(() -> new CustomException("User not found with username: " + email));

        return new
                org.springframework.security
                        .core.userdetails.User(user.getEmail(), user.getPassword(), user.getEnabled(), true, true, true, getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        List<Role> userRoles = user.getRoles();
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : userRoles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
}
