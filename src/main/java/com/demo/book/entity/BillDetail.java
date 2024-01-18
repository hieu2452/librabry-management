package com.demo.book.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class BillDetail {
    @EmbeddedId
    private BillDetailKey billDetailKey;

    private final LocalDateTime borrowedDate = LocalDateTime.now();
    private int quantity;

    public BillDetail() {

    }

    public BillDetail(BillDetailKey billDetailKey, Bill bill, Book book, int quantity) {
        this.billDetailKey = billDetailKey;
        this.bill = bill;
        this.book = book;
        this.quantity = quantity;
    }

    @ManyToOne(fetch = FetchType.LAZY,cascade= CascadeType.ALL)
    @MapsId("billId")
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    public LocalDateTime getBorrowedDate() {
        return borrowedDate;
    }

    public Bill getBill() {
        return bill;
    }

    public BillDetailKey getBillDetailKey() {
        return billDetailKey;
    }

    public void setBillDetailKey(BillDetailKey billDetailKey) {
        this.billDetailKey = billDetailKey;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BillDetailKey getBorrowedBookKey() {
        return billDetailKey;
    }

    public void setBorrowedBookKey(BillDetailKey billDetailKey) {
        this.billDetailKey = billDetailKey;
    }
}
