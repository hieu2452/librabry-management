package com.demo.book.helper;

import com.demo.book.domain.dto.BookDto;
import com.demo.book.entity.Book;

public class BookMappingHelper {
    public static BookDto map(final Book book) {

        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .quantity(book.getQuantity())
                .description(book.getDescription())
                .author(book.getAuthor())
                .addedDate(book.getAddedDate())
                .category(book.getCategory().getCategoryName())
                .publisher(book.getPublisher().getName())
                .language(book.getLanguage())
                .build();

    }


}
