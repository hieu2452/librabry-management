package com.demo.book.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookFactory{
    @Autowired
    private LecturerBookFactory lecturerBookFactory;
    @Autowired
    private ScienceBookFactory scienceBookFactory;
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
