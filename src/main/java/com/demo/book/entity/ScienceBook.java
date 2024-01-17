package com.demo.book.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SCIENCE")
public class ScienceBook extends Book {
    public ScienceBook () {}
    public static class BookBuilder extends Builder { public BookBuilder(String title, float price, String author) {
            super(title, price, author);
        }

        public ScienceBook build() { return new ScienceBook(this); }
    }

    private ScienceBook(BookBuilder builder) {
        super(builder);
    }

}
