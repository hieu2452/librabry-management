package com.demo.book.service;

import com.demo.book.dto.BillDto;
import com.demo.book.entity.Bill;

public interface BillService {
    String createBill(BillDto billDto);
}
