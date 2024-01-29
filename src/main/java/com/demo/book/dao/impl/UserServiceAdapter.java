package com.demo.book.dao.impl;

import com.demo.book.domain.UserDto;
import com.demo.book.entity.Member;
import com.demo.book.entity.Staff;
import com.demo.book.service.AdminService;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceAdapter implements AdminService {
    private final UserDAO userDAO = UserDAO.getInstance();
    @Override
    public void createLibrarianUser(Staff staff) {

    }

    @Override
    public void createMemberUser(Member member) {

    }

    @Override
    public List<UserDto> getUsers(Integer minAge, Integer maxAge, String userType) {
        List<UserDto> users = userDAO.findUser(minAge,maxAge);
        if(userType.equals("") || users.isEmpty()) return users;
        return users.stream().filter(user-> user.getUserType().equalsIgnoreCase(userType)).collect(Collectors.toList());
    }
}
