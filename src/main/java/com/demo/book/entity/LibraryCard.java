package com.demo.book.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class LibraryCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private final LocalDateTime createdDate = LocalDateTime.now();
    private String status;
    private int bookAvailable;
    @OneToOne(mappedBy = "libraryCard")
    private User user;
    public LibraryCard() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBookAvailable() {
        return bookAvailable;
    }

    public void setBookAvailable(int bookAvailable) {
        this.bookAvailable = bookAvailable;
    }
}
