package com.demo.book.factory;

import com.demo.book.entity.Book;
import com.demo.book.entity.LecturerBook;
import com.demo.book.repository.BookRepository;
import com.demo.book.repository.LecturerBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("lecturerBookImpl")
public class ILecturerBookImpl implements IBook<LecturerBook> {
    @Autowired
    private LecturerBookRepository lecRepository;
    @Override
    public List<LecturerBook> getBook() {
        return lecRepository.findAll();
    }
}
