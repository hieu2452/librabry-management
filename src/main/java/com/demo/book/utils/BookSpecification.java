package com.demo.book.utils;

import com.demo.book.dto.BookFilter;
import com.demo.book.entity.Book;
import com.demo.book.entity.Category;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class BookSpecification {
    public static Specification<Book> filterBy(BookFilter bookFilter) {
        return null;
    }

    public static Specification<Book> hasCategory(String category) {
        return (root, query, cb) -> {
            Join<Category,Book> join = root.join("category", JoinType.LEFT);
            return cb.equal(join.get("categoryName"),category);
        };
    }

    public static Specification<Book> hasPublisher(String publisher) {
        return (root, query, cb) -> {
            Join<Category,Book> join = root.join("publisher", JoinType.LEFT);
            return cb.like(join.get("name"),publisher);
        };
    }
}
