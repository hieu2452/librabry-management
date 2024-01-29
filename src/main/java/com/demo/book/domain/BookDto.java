package com.demo.book.domain;

import lombok.Data;

@Data
public class BookDto {
    private long id;
    private String title;
    private String subTitle;
    private String description;
    private String author;
    private String type;
    private String imageUrl;
    private String category;
    private String publisher;
    private int quantity;
    private String language;
}
