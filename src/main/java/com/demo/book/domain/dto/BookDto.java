package com.demo.book.domain.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookDto {
    private long id;
    @NotBlank
    private String title;
    private String subTitle;
    private String description;
    @NotBlank
    private String author;
    private String category;
    private String publisher;
    @NotNull
    @Min(1)
    private int quantity;
    private String language;
}
