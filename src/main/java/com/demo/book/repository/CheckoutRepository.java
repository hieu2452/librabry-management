package com.demo.book.repository;

import com.demo.book.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<Checkout,Long> {
}
