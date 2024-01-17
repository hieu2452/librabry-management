package com.demo.book.factory;

import com.demo.book.entity.ScienceBook;
import com.demo.book.repository.ScienceBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component("scienceBookImpl")
public class IScienceBookImpl implements IBook<ScienceBook>{
    @Autowired
    private ScienceBookRepository scienceBookRepository;
    @Override
    public List<ScienceBook> getBook() {
        return scienceBookRepository.findAll();
    }
}
