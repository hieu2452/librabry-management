package com.demo.book.factory;

import com.demo.book.entity.Book;
import com.demo.book.entity.ScienceBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

public class ScienceBookFactory extends BookAbstractFactory {
    @Override
    public IBook createBook() {
        return new IScienceBookImpl();
    }
}
