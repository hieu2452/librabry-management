package com.demo.book.factory;

import com.demo.book.entity.LectureBook;
import com.demo.book.entity.ScienceBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookFactory{
    public BookAbstractFactory getFactory(String bookType){
        switch (bookType) {
            case "lecturer" -> {
                return new LecturerBookFactory();
            }
            case "science" -> {
                return new ScienceBookFactory();
            }
            default -> {return null;}
        }
    }
}
