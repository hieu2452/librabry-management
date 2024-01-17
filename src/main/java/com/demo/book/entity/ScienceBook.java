package com.demo.book.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SCIENCE")
public class ScienceBook extends Book {
    private String fieldOfScience;
    public ScienceBook () {}
    public static class BookBuilder extends Builder {
        private String fieldOfScience;
        public BookBuilder(String title, float price, String author) {
            super(title, price, author);
        }

        public BookBuilder fieldOfScience(String fieldOfScience) {
            this.fieldOfScience = fieldOfScience;
            return this;
        }
        public ScienceBook build() { return new ScienceBook(this); }
    }

    private ScienceBook(BookBuilder builder) {
        super(builder);
        this.fieldOfScience = builder.fieldOfScience;
    }

    public String getFieldOfScience() {
        return fieldOfScience;
    }
}
