package com.demo.book.factory.UserFactory;

import com.demo.book.entity.Librarian;
import com.demo.book.entity.User;

public class LibrarianFactory extends UserAbstractFactory{
    @Override
    public User createUser() {
        return new Librarian();
    }
}
