package com.demo.book.domain.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private long id;
    @NotBlank
    private String title;
    private String description;
    @NotBlank
    private String author;
    private String category;
    private String publisher;
    private LocalDateTime addedDate;
    @NotNull
    @Min(1)
    private int quantity;
    private String language;
}
