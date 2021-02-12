package com.marcomnrq.ecommerce.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;

    private Float unitPrice;

    private Float totalPrice;
}
