package com.demo.book.service;

import com.demo.book.domain.dto.BillDto;
import com.demo.book.domain.response.BorrowResponse;
import com.demo.book.entity.Bill;
import com.demo.book.entity.BillDetail;

import java.util.List;

public interface BillService {
    String createBill(BillDto billDto);
    List<Bill> findAll();

    List<BorrowResponse> findBillDetail(long billId);
}
