package com.demo.book.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageableResponse<T> {
    List<T> content;
    long totalItems;
    long totalPages;
    long currentPage;
}
