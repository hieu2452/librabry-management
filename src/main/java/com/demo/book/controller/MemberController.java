package com.demo.book.controller;

import com.demo.book.dao.impl.MemberDAO;
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
    private final MemberDAO memberDAO = MemberDAO.getInstance();
    @Autowired
    private AdminService adminService;

    @GetMapping("/get")
    public ResponseEntity<?> getMember(@RequestParam(defaultValue = "0") int minAge,
                                       @RequestParam(defaultValue = "0") int maxAge,
                                       @RequestParam(defaultValue = "") String type) throws NotSupportedException {
        return ResponseEntity.ok(memberDAO.findMembers(minAge,maxAge,type));
    }

    @PostMapping("/create-member")
    public ResponseEntity<?> createMemberUser(@RequestBody Member user) {
        return ResponseEntity.ok(adminService.createMemberUser(user));
    }
}
