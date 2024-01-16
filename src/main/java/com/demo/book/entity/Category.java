package com.demo.book.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String categoryName;

    public Category() {

    }

    public static class Builder {
        private final String categoryName;
        public Builder(String categoryName) {
            this.categoryName = categoryName;
        }
        public Category build() {
            return new Category(this);
        }

    }

    private Category(Builder builder) {
        this.categoryName = builder.categoryName;
    }

    public long getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
