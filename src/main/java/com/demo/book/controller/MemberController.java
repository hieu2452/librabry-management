package com.demo.book.controller;

import com.demo.book.dao.impl.MemberDAO;
import jakarta.transaction.NotSupportedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/member")
@RestController
@CrossOrigin
public class MemberController {
    private final MemberDAO memberDAO = MemberDAO.getInstance();

    @GetMapping("/get")
    public ResponseEntity<?> getMember(@RequestParam(defaultValue = "0") int minAge,
                                       @RequestParam(defaultValue = "0") int maxAge,
                                       @RequestParam(defaultValue = "") String type) throws NotSupportedException {
        return ResponseEntity.ok(memberDAO.findMembers(minAge,maxAge,type));
    }


}
