package com.demo.book.repository;

import com.demo.book.entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillDetailRepository extends JpaRepository<BillDetail,Long> {

    @Query("SELECT bd FROM BillDetail bd JOIN bd.bill b WHERE b.id =?1")
    List<BillDetail> findByBillId(long id);
}
