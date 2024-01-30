package com.demo.book.controller;

import com.demo.book.entity.Member;
import com.demo.book.service.AdminService;
import com.demo.book.service.StaffService;
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
    @Autowired
    private StaffService staffService;

    @GetMapping("")
    public ResponseEntity<?> getMember(@RequestParam(defaultValue = "0") int minAge,
                                       @RequestParam(defaultValue = "0") int maxAge) throws NotSupportedException {
        return ResponseEntity.ok(adminService.getUsers(minAge,maxAge,"MEMBER"));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMemberUser(@RequestBody Member user) {
        adminService.createMemberUser(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/lb-card/{id}")
    public ResponseEntity<?> getMemberByLibraryCard(@PathVariable long id) {

        return ResponseEntity.ok(staffService.findMemberByLibraryCard(id));
    }
}
