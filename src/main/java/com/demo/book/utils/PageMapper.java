package com.demo.book.utils;

import com.demo.book.domain.response.PageableResponse;
import org.springframework.data.domain.Page;

public class PageMapper{
    public static <T> PageableResponse<T> mapPageable(Page<T> page){

        PageableResponse<T> response = new PageableResponse<>();
        response.setContent(page.getContent());
        response.setTotalItems(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setCurrentPage(page.getPageable().getPageNumber());
        return response;
    }
}
