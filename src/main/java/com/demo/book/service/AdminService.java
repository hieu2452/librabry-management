package com.demo.book.service;

import com.demo.book.dto.UserDto;
import com.demo.book.entity.Librarian;
import com.demo.book.entity.Member;
import com.demo.book.entity.User;

import java.util.List;

public interface AdminService {
    Librarian createLibrarianUser(Librarian librarian);
    Member createMemberUser(Member member);
    List<UserDto> getUsers();
}
