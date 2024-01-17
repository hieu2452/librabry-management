package com.demo.book.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class LecturerBookFactory extends BookAbstractFactory {
    @Autowired
    @Qualifier("lecturerBookImpl")
    private IBook lecturerBook;
    @Override
    public IBook createBook() {
        return lecturerBook;
    }

}
