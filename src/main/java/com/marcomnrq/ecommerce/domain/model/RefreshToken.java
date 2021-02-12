package com.marcomnrq.ecommerce.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    private String token;
}