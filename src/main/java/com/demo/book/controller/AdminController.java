package com.demo.book.controller;

import com.demo.book.dao.impl.UserDAO;
import com.demo.book.entity.Staff;
import com.demo.book.entity.Member;
import com.demo.book.service.AdminService;
import jakarta.transaction.NotSupportedException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/create-librarian")
    public ResponseEntity<?> createLibrarianUser(@Valid @RequestBody Staff user) {
        return ResponseEntity.ok(adminService.createLibrarianUser(user));
    }

    @GetMapping("/get-user")
    public ResponseEntity<?> getUser(@RequestParam(defaultValue = "0") int minAge,
                                       @RequestParam(defaultValue = "0") int maxAge,
                                       @RequestParam(defaultValue = "") String type) {
        return ResponseEntity.ok(adminService.getUsers(minAge,maxAge,type));
    }
}
