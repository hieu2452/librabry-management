package com.demo.book.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CheckoutDetailDto {
    private long bookId;
    private int quantity;
}
