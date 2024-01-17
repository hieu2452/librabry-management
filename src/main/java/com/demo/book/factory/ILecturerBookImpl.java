package com.demo.book.factory;

import com.demo.book.entity.Book;
import com.demo.book.entity.LectureBook;
import com.demo.book.repository.LecturerBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("lecturerBookImpl")
public class ILecturerBookImpl implements IBook {
    @Autowired
    private LecturerBookRepository lecRepository;
    @Override
    public Book createBook() {
        return new LectureBook();
    }
}
