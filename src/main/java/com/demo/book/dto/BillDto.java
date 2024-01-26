package com.demo.book.dto;

import com.demo.book.entity.Book;
import lombok.Data;

import java.util.List;
@Data
public class BillDto {
    private long userId;
    private List<BillDetailDto> books;
}
