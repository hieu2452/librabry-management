package com.demo.book.service;

import com.demo.book.dto.UserDto;
import com.demo.book.entity.Staff;
import com.demo.book.entity.Member;

import java.util.List;

public interface AdminService {
    Staff createLibrarianUser(Staff staff);
    Member createMemberUser(Member member);
    List<UserDto> getUsers();
}
