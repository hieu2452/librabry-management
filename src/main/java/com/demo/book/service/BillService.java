package com.demo.book.service;

import com.demo.book.domain.BillDto;
import com.demo.book.entity.Bill;

import java.util.List;

public interface BillService {
    String createBill(BillDto billDto);
    List<Bill> findAll();
}
