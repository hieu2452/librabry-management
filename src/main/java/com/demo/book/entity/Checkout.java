package com.demo.book.entity;

import com.demo.book.entity.enums.BillStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "checkout")
@Data
public class Checkout {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private final LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime returnedDate;
    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;
    @Enumerated(EnumType.STRING)
    private BillStatus Status;

    @JsonIgnore
    @OneToMany(mappedBy = "checkOut",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CheckoutDetail> checkoutDetails = new ArrayList<>();
    public Checkout() {

    }

    public Checkout(long id, BillStatus status) {
        this.id = id;
        Status = status;
    }
}
