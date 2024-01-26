package com.demo.book.controller;

import com.demo.book.dao.impl.UserDAO;
import com.demo.book.entity.Member;
import com.demo.book.service.AdminService;
import jakarta.transaction.NotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/member")
@RestController
@CrossOrigin
public class MemberController {
    @Autowired
    private AdminService adminService;


    @GetMapping("/get")
    public ResponseEntity<?> getMember(@RequestParam(defaultValue = "0") int minAge,
                                       @RequestParam(defaultValue = "0") int maxAge) throws NotSupportedException {
        return ResponseEntity.ok(adminService.getUsers(minAge,maxAge,"MEMBER"));
    }

    @PostMapping("/create-member")
    public ResponseEntity<?> createMemberUser(@RequestBody Member user) {
        return ResponseEntity.ok(adminService.createMemberUser(user));
    }
}
