package com.demo.book.controller;

import com.demo.book.domain.BillDto;
import com.demo.book.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/bill")
public class BillController {
    @Autowired
    private BillService billService;

    @PostMapping("/create")
    public ResponseEntity<?> createBill(@RequestBody BillDto billDto) {
        return ResponseEntity.ok(billService.createBill(billDto));
    }
    @GetMapping("")
    public ResponseEntity<?> getBills() {
        return ResponseEntity.ok(billService.findAll());
    }
}
