package com.demo.book.entity;

import jakarta.persistence.*;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String subTitle;
    private String description;
    private final LocalDateTime createdDate = LocalDateTime.now();
    private float price;
    private String imageUrl;
    public Book() {

    }
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "book_category", joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id",referencedColumnName = "id"))
    private List<Category> categories = new ArrayList<>();

    public static class Builder {
        private final String title;
        private String subTitle;
        private String description;
        private final float price;
        private String imageUrl;

        public Builder(String title,float price) {
            this.title = title;
            this.price = price;
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

        public Book build() {
            return new Book(this);
        }

    }

    private Book(Builder builder) {
        this.title = builder.title;
        this.price = builder.price;
        this.subTitle = builder.subTitle;
        this.description = builder.description;
        this.imageUrl = builder.imageUrl;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public float getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Category> getCategories() {
        return categories;
    }

}
