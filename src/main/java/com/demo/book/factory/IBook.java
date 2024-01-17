package com.demo.book.factory;

import java.util.List;

public interface IBook<T> {
    List<T> getBook();
}
