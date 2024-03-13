package com.demo.book.entity;

import com.demo.book.entity.enums.BorrowedBookStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class CheckoutDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "borrowed_date")
    private final LocalDateTime borrowedDate = LocalDateTime.now();
    @Column(name = "returned_date")
    private LocalDateTime returnedDate;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private BorrowedBookStatus status;
    public CheckoutDetail() {

    }
    public CheckoutDetail(int quantity, BorrowedBookStatus status, Checkout checkOut, Book book) {
        this.quantity = quantity;
        this.status = status;
        this.checkOut = checkOut;
        this.book = book;
    }


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checkout_id")
    private Checkout checkOut;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;

}
