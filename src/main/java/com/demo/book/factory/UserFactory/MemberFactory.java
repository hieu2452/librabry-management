package com.demo.book.factory.UserFactory;

import com.demo.book.entity.Member;
import com.demo.book.entity.User;

public class MemberFactory extends UserAbstractFactory{
    @Override
    public User createUser() {
        return new Member();
    }
}
