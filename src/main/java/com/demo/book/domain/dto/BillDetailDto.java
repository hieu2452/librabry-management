package com.demo.book.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillDetailDto {
    private long bookId;
    private int quantity;
}
