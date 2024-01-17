package com.demo.book.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ScienceBookFactory extends BookAbstractFactory{
    @Autowired
    @Qualifier("scienceBookImpl")
    private IBook scienceBook;

    @Override
    public IBook createBook() {
        return scienceBook;
    }
}
