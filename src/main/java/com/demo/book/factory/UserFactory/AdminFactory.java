package com.demo.book.factory.UserFactory;

import com.demo.book.entity.Admin;
import com.demo.book.entity.User;

public class AdminFactory extends UserAbstractFactory{
    //truyen userType
    @Override
    public User createUser() {
        return new Admin();
    }
}