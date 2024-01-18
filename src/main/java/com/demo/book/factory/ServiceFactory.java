package com.demo.book.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ServiceFactory extends ServiceAbstractFactory {
    @Autowired
    private IBook bookImpl;
    @Autowired
    private IUser userImpl;

    @Override
    public IBook createIBook() {
        return bookImpl;
    }

    @Override
    public IUser createIUser() {
        return userImpl;
    }
}
