package com.demo.book.entity;

import javax.persistence.*;

import lombok.Data;

import java.time.Instant;

@Entity(name = "refresh_token")
@Data
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    private Staff staff;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name = "expiry_date",nullable = false)
    private Instant expiryDate;

    public RefreshToken() {
    }
}