package com.demo.book.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private final LocalDateTime createdDate = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Bill() {

    }
    public long getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
