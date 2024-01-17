package com.demo.book.factory;

import com.demo.book.entity.ScienceBook;
import org.springframework.stereotype.Component;

import java.util.List;
@Component("scienceBookImpl")
public class IScienceBookImpl implements IBook<ScienceBook>{
    @Override
    public List<ScienceBook> getBook() {
        return null;
    }
}
