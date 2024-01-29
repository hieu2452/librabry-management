package com.demo.book.domain;

import lombok.Data;

import java.util.List;
@Data
public class BillDto {
    private long userId;
    private List<BillDetailDto> books;
}
