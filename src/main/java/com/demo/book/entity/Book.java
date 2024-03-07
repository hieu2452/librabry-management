package com.demo.book.entity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;

import java.io.Serializable;
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
    @NotBlank(message = "book title is mandatory")
    private String title;
    private String description;
    private final LocalDateTime addedDate = LocalDateTime.now();
    @NotBlank(message = "book author is mandatory")
    private String author;
    @NotBlank(message = "book language is mandatory")
    private String language;
    @NotNull(message = "book title is mandatory")
    @Min(1)
    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    public Book() {

    }
    public static class Builder {
        private long id;
        private final String title;
        private final String author;
        private String language;
        private String description;
        private Category category;
        private Publisher publisher;
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

        public Builder description(String description) {
            this.description = description;
            return this;
        }
        public Builder id(long id) {
            this.id = id;
            return this;
        }
        public Builder category(Category category) {
            this.category = category;
            return this;
        }
        public Builder publisher(Publisher publisher) {
            this.publisher = publisher;
            return this;
        }

        public Book build() {
            return new Book(this);
        }

    }

    public Book(Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.author = builder.author;
        this.category = builder.category;
        this.quantity = builder.quantity;
        this.language = builder.language;
        this.publisher = builder.publisher;
        this.id = builder.id;
    }

}

