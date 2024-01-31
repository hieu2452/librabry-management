package com.demo.book.repository;

import com.demo.book.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher,Long> {
    Optional<Publisher> findByName(String name);
}
