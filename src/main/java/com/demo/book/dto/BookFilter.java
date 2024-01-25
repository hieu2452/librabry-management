package com.demo.book.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
@Getter
@Setter
public class BookFilter {
    String category = "all";
    String publisher = "all";
    String language ="all";
    int pageSize = 10;
    int pageNumber = 0;
}
