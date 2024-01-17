package com.demo.book.factory;

import com.demo.book.entity.LectureBook;
import com.demo.book.entity.ScienceBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookFactory<T>{
    @Autowired
    private BookAbstractFactory<LectureBook> lecturerBookFactory;
    @Autowired
    private BookAbstractFactory<ScienceBook> scienceBookFactory;
    public BookAbstractFactory getFactory(String bookType){
        switch (bookType) {
            case "lecturer" -> {
                return lecturerBookFactory;
            }
            case "science" -> {
                return scienceBookFactory;
            }
            default -> {return null;}
        }
    }
}
