package com.demo.book.domain.dto;

import lombok.Data;

import java.util.List;
@Data
public class CheckoutDto {
    private long userId;
    private List<CheckoutDetailDto> books;
}
