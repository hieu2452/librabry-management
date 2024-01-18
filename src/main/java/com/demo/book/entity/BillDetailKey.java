package com.demo.book.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class BillDetailKey implements Serializable {
    @Column(name = "bill_id",nullable = false)
    private long billId;
    @Column(name = "book_id",nullable = false)
    private long bookId;
    public BillDetailKey() {

    }
    public BillDetailKey(long billId, long bookId) {
        this.billId = billId;
        this.bookId = bookId;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public long getBillId() {
        return billId;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }
}
