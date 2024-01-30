package com.demo.book.service.impl;

import com.demo.book.entity.Member;
import com.demo.book.exception.UserNotFoundException;
import com.demo.book.repository.MemberRepository;
import com.demo.book.service.StaffService;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private MemberRepository memberRepository;
    @Override
    public Member findMemberByLibraryCard(long id) {
        return memberRepository.findByLibraryCard(id).orElseThrow(() -> new UserNotFoundException("user not found"));
    }
}
