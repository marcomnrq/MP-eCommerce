package com.marcomnrq.ecommerce.domain.repository;

import com.marcomnrq.ecommerce.domain.model.Cart;
import com.marcomnrq.ecommerce.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
