package com.demo.book.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class LibraryCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private final LocalDateTime createdDate = LocalDateTime.now();
    private String status;
    private int bookAvailable;
    @OneToOne(mappedBy = "libraryCard")
    private Member member;
    public LibraryCard() {

    }

}
