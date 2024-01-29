package com.demo.book.utils;

import com.demo.book.domain.BookFilter;
import com.demo.book.entity.Book;
import com.demo.book.entity.Category;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<Book> filterBy(BookFilter bookFilter) {
        return null;
    }

    public static Specification<Book> byCategory(String category) {
        return (root, query, cb) -> {
            Join<Category,Book> join = root.join("category", JoinType.LEFT);
            return cb.like(join.get("categoryName"),"%"+category+"%");
        };
    }

    public static Specification<Book> byPublisher(String publisher) {
        return (root, query, cb) -> {
            Join<Category,Book> join = root.join("publisher", JoinType.LEFT);
            return cb.like(join.get("name"),"%"+publisher+"%");
        };
    }

    public static Specification<Book> byLanguage(String language) {
        return (root, query, cb) -> cb.like(root.get("language"),"%"+language+"%");
    }
    public static Specification<Book> byTitle(String title) {
        return (root, query, cb) -> cb.like(root.get("title"),"%"+title+"%");
    }

    public static Specification<Book> byAuthor(String author) {
        return (root, query, cb) -> cb.like(root.get("author"),"%"+author+"%");
    }
}
