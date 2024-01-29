package com.demo.book.entity;

import com.demo.book.entity.enums.BillStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "bills")
@Data
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private final LocalDateTime createdDate = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @Enumerated(EnumType.STRING)
    private BillStatus Status;
    public Bill() {

    }
}
