package com.demo.book.factory;

import org.springframework.stereotype.Component;

@Component
public abstract class BookAbstractFactory<T> {
        public abstract IBook<T> createBook();
}
