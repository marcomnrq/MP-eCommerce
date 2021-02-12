package com.marcomnrq.ecommerce.domain.repository;

import com.marcomnrq.ecommerce.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
