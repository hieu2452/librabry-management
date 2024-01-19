package com.demo.book.entity;

import jakarta.persistence.*;
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
    private float price;
    private String author;
    private String imageUrl;
    private String language;
    private int quantity;
    public Book() {

    }
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "book_category", joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id",referencedColumnName = "id"))
    private List<Category> categories = new ArrayList<>();


    public static class Builder {
        private final String title;
        private final String author;
        private String subTitle;
        private String language;
        private String description;
        private final float price;
        private String imageUrl;
        private List<Category> categories;
        private final int quantity;
        public Builder(String title,float price, String author,int quantity) {
            this.title = title;
            this.price = price;
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
        public Builder categories(List<Category> categories) {
            this.categories = categories;
            return this;
        }

        public Book build() {
            return new Book(this);
        }

    }

    public Book(Builder builder) {
        this.title = builder.title;
        this.price = builder.price;
        this.subTitle = builder.subTitle;
        this.description = builder.description;
        this.imageUrl = builder.imageUrl;
        this.author = builder.author;
        this.categories = builder.categories;
        this.quantity = builder.quantity;
        this.language = builder.language;
    }

}

