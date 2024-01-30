package com.demo.book.domain.params;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookFilter {
    String category = "all";
    String publisher = "all";
    String language ="all";
    String keyword = "";
    int pageSize = 10;
    int pageNumber = 0;
}
