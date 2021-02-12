package com.marcomnrq.ecommerce.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cart_items")
public class CartItem extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;

    private Float unitPrice;

    private Float totalPrice;
}

