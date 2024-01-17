package com.demo.book.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("LECTURER")
public class LectureBook extends Book {
    private String subject;
    public LectureBook() {

    }
    public static class BookBuilder extends Builder {
        private String subject;
        public BookBuilder(String title, float price, String author) {
            super(title, price, author);
        }

        public BookBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }
        public LectureBook build() { return new LectureBook(this); }
    }

    private LectureBook(BookBuilder builder) {
        super(builder);
        this.subject = builder.subject;
    }

    public String getSubject() {
        return subject;
    }

}
