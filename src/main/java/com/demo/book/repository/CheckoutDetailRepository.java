package com.demo.book.repository;

import com.demo.book.entity.CheckoutDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CheckoutDetailRepository extends JpaRepository<CheckoutDetail,Long> {

    @Query("SELECT bd FROM CheckoutDetail bd JOIN bd.checkOut b WHERE b.id =?1")
    List<CheckoutDetail> findByCheckoutId(long id);
}