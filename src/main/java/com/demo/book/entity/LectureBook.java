package com.demo.book.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("LECTURER")
public class LectureBook extends Book {
    public LectureBook() {

    }
    public static class BookBuilder extends Builder {
        public BookBuilder(String title, float price, String author) {
            super(title, price, author);
        }

        public LectureBook build() { return new LectureBook(this); }
    }

    private LectureBook(BookBuilder builder) {
        super(builder);}



}
