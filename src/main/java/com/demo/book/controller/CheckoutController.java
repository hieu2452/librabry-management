package com.demo.book.controller;

import com.demo.book.domain.dto.CheckoutDto;
import com.demo.book.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    @Autowired
    private CheckoutService checkout;

    @PostMapping("create")
    public ResponseEntity<?> createBill(@RequestBody CheckoutDto checkoutDto) {
        return ResponseEntity.ok(checkout.createBill(checkoutDto));
    }
    @GetMapping("")
    public ResponseEntity<?> getBills() {
        return ResponseEntity.ok(checkout.findAll());
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<?> getBillDetail(@PathVariable long id) {
        return ResponseEntity.ok(checkout.findBillDetail(id));
    }

    @PatchMapping("return/{id}")
    public ResponseEntity<?> returnBooks(@RequestBody List<Long> bookIds,@PathVariable long id) {
        checkout.returnBook(bookIds,id);
        return ResponseEntity.noContent().build();
    }
}
