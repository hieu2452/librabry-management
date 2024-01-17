package com.demo.book.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("LECTURER")
public class LecturerBook extends Book {
    private String subject;
    private String institution;
    public LecturerBook() {

    }
    public static class BookBuilder extends Builder {
        private String subject;
        private String institution;
        public BookBuilder(String title, float price, String author) {
            super(title, price, author);
        }

        public BookBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }
        public BookBuilder institution(String institution) {
            this.institution = institution;
            return this;
        }
        public LecturerBook build() { return new LecturerBook(this); }
    }

    private LecturerBook(BookBuilder builder) {
        super(builder);
        this.institution = builder.institution;
        this.subject = builder.subject;
    }

    public String getSubject() {
        return subject;
    }

    public String getInstitution() {
        return institution;
    }
}
