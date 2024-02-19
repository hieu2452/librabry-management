package com.demo.book.service;

import com.demo.book.domain.dto.CheckoutDto;
import com.demo.book.domain.response.BorrowResponse;
import com.demo.book.domain.response.MessageResponse;

import java.util.List;

public interface CheckoutService {
    MessageResponse createBill(CheckoutDto checkoutDto);
    List<com.demo.book.entity.Checkout> findAll();

    List<BorrowResponse> findBillDetail(long billId);

    void returnBook(List<Long> bookIds, long billId);
}
