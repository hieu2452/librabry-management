package com.demo.book.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PageableResponse<T> {
    List<T> content;
    long totalItems;
    long totalPages;
}
