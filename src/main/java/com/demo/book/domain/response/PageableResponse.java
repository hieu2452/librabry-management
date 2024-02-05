package com.demo.book.domain.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PageableResponse<T> {
    List<T> content;
    long totalItems;
    long totalPages;
    long currentPage;
}
