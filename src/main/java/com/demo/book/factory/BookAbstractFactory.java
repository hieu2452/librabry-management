package com.demo.book.factory;

import com.demo.book.entity.Book;
import org.springframework.stereotype.Component;

@Component
public abstract class BookAbstractFactory {
        public abstract IBook createBook();
}
