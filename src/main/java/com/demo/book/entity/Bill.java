package com.demo.book.entity;

import com.demo.book.entity.enums.BillStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @JsonIgnore
    @OneToMany(mappedBy = "bill",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BillDetail> billDetails = new ArrayList<>();
    public Bill() {

    }

    public Bill(long id, BillStatus status) {
        this.id = id;
        Status = status;
    }
}
