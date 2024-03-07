package com.demo.book.service;

import com.demo.book.domain.dto.StaffDto;
import com.demo.book.domain.dto.UserDto;
import com.demo.book.entity.Staff;
import com.demo.book.entity.Member;

import java.util.List;

public interface AdminService {
    void createLibrarianUser(StaffDto staff);
    void createMemberUser(Member member);
//    List<UserDto> getUsers(Integer minAge,Integer maxAge,String userType);
}
