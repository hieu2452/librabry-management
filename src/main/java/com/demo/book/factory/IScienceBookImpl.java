package com.demo.book.factory;

import com.demo.book.entity.Book;
import com.demo.book.entity.ScienceBook;
import com.demo.book.repository.ScienceBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
public class IScienceBookImpl implements IBook{
    @Override
    public Book createBook() {
        return new ScienceBook();
    }
}
