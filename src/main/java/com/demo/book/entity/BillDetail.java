package com.demo.book.entity;

import com.demo.book.entity.enums.BillStatus;
import com.demo.book.entity.enums.BorrowedBookStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class BillDetail {
    @EmbeddedId
    @JsonIgnore
    private BillDetailKey billDetailKey;
    private final LocalDateTime borrowedDate = LocalDateTime.now();
    private int quantity;
    @Enumerated(EnumType.STRING)
    private BorrowedBookStatus status;
    public BillDetail() {

    }
    public BillDetail(BillDetailKey billDetailKey, int quantity, BorrowedBookStatus status, Bill bill, Book book) {
        this.billDetailKey = billDetailKey;
        this.quantity = quantity;
        this.status = status;
        this.bill = bill;
        this.book = book;
    }

    public BillDetail(int quantity, BorrowedBookStatus status, Bill bill, Book book) {
        this.quantity = quantity;
        this.status = status;
        this.bill = bill;
        this.book = book;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("billId")
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

}
