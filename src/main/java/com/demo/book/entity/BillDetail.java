package com.demo.book.entity;

import com.demo.book.entity.enums.BillStatus;
import com.demo.book.entity.enums.BorrowedBookStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class BillDetail {
    @EmbeddedId
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

    @ManyToOne(fetch = FetchType.LAZY,cascade= CascadeType.ALL)
    @MapsId("billId")
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

}
