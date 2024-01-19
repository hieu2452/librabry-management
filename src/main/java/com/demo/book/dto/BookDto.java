package com.demo.book.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class BookDto {
    private long id;
    private String title;
    private String subTitle;
    private String description;
    private float price;
    private String author;
    private String type;
    private String imageUrl;
    private List<String> categories;
    private int quantity;
    private String language;
}
