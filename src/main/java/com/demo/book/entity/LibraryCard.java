package com.demo.book.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "library_card")
public class LibraryCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "created_date")
    private final LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "status")
    private String status;
    @Column(name = "book_available")
    private int bookAvailable;
    @JsonIgnore
    @OneToOne(mappedBy = "libraryCard")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Member member;
    public LibraryCard() {

    }

    public LibraryCard(String status, int bookAvailable) {
        this.status = status;
        this.bookAvailable = bookAvailable;
    }

}
