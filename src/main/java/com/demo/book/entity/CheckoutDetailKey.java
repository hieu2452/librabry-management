package com.demo.book.entity;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class CheckoutDetailKey implements Serializable {
    @Column(name = "checkout_id",nullable = false)
    private long checkoutId;
    @Column(name = "book_id",nullable = false)
    private long bookId;
    public CheckoutDetailKey() {

    }
    public CheckoutDetailKey(long checkoutId, long bookId) {
        this.checkoutId = checkoutId;
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

}
