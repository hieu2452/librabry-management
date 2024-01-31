package com.demo.book.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class BookTest {
    private static final String TITLE = "Harry Potter";
    private static final String AUTHOR = "Rowling";
    private static final String LANGUAGE = "English";
    private static final int QUANTITY = 2;
    @Test
    public void build(){
        Book book = new Book.Builder(TITLE,AUTHOR,QUANTITY).language(LANGUAGE).build();

        assertEquals(TITLE,book.getTitle());
        assertEquals(AUTHOR,book.getAuthor());
        assertEquals(LANGUAGE,book.getLanguage());
    }

}
