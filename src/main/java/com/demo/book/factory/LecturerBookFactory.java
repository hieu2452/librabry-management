package com.demo.book.factory;

import com.demo.book.entity.Book;
import com.demo.book.entity.LectureBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

public class LecturerBookFactory extends BookAbstractFactory {
    @Override
    public IBook createBook() {
        return new ILecturerBookImpl();
    }

}
