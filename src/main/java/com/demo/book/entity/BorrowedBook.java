package com.demo.book.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class BorrowedBook {
    @EmbeddedId
    private BorrowedBookKey borrowedBookKey;

    private final LocalDateTime borrowedDate = LocalDateTime.now();

    public BorrowedBook() {

    }

    @ManyToOne(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BorrowedBookKey getBorrowedBookKey() {
        return borrowedBookKey;
    }

    public void setBorrowedBookKey(BorrowedBookKey borrowedBookKey) {
        this.borrowedBookKey = borrowedBookKey;
    }
}
