package com.demo.book.repository;

import com.demo.book.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher,Long> {
    Publisher findByName(String name);
}
