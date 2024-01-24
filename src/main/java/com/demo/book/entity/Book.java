package com.demo.book.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String subTitle;
    private String description;
    private final LocalDateTime addedDate = LocalDateTime.now();
    private String author;
    private String imageUrl;
    private String language;
    private int quantity;
    public Book() {

    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    @Valid
    private Category category;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    public static class Builder {
        private final String title;
        private final String author;
        private String subTitle;
        private String language;
        private String description;
        private String imageUrl;
        private Category category;
        private final int quantity;
        public Builder(String title, String author,int quantity) {
            this.title = title;
            this.author = author;
            this.quantity = quantity;
        }
        public Builder language(String language) {
            this.language = language;
            return this;
        }
        public Builder subTitle(String subTitle) {
            this.subTitle = subTitle;
            return this;
        }
        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }
        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Book build() {
            return new Book(this);
        }

    }

    public Book(Builder builder) {
        this.title = builder.title;
        this.subTitle = builder.subTitle;
        this.description = builder.description;
        this.imageUrl = builder.imageUrl;
        this.author = builder.author;
        this.category = builder.category;
        this.quantity = builder.quantity;
        this.language = builder.language;
    }

}

