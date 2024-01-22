package com.demo.book.factory.UserFactory;

import com.demo.book.entity.Staff;
import com.demo.book.entity.User;

public class StaffFactory extends UserAbstractFactory{
    @Override
    public User createUser() {
        return new Staff();
    }
}
