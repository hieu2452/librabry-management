package com.demo.book.controller;

import com.demo.book.entity.Librarian;
import com.demo.book.entity.Member;
import com.demo.book.entity.User;
import com.demo.book.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/create-librarian")
    public ResponseEntity<?> createLibrarianUser(@RequestBody Librarian user) {
        return ResponseEntity.ok(adminService.createLibrarianUser(user));
    }

    @PostMapping("/create-member")
    public ResponseEntity<?> createMemberUser(@RequestBody Member user) {
        return ResponseEntity.ok(adminService.createMemberUser(user));
    }
}
