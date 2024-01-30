package com.demo.book.domain.dto;

import lombok.Data;

import java.util.List;
@Data
public class BillDto {
    private long userId;
    private List<BillDetailDto> books;
}
