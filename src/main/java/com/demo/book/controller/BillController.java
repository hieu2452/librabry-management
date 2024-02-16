package com.demo.book.controller;

import com.demo.book.domain.dto.BillDto;
import com.demo.book.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getBillDetail(@PathVariable long id) {
        return ResponseEntity.ok(billService.findBillDetail(id));
    }

    @PatchMapping("/return/{id}")
    public ResponseEntity<?> returnBooks(@RequestBody List<Long> bookIds,@PathVariable long id) {
        billService.returnBook(bookIds,id);
        return ResponseEntity.noContent().build();
    }
}
