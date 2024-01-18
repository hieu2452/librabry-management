package com.demo.book.controller;

import com.demo.book.dto.BillDto;
import com.demo.book.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/bill")
public class BillController {
    @Autowired
    private BillService billService;

    @PostMapping("/create-bill")
    public ResponseEntity<?> createBill(@RequestBody BillDto billDto) {
        return ResponseEntity.ok(billService.createBill(billDto));
    }
}
