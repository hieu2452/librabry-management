package com.demo.book.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillDetailDto {
    private long bookId;
    private int quantity;
}
