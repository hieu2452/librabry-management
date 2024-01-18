package com.demo.book.repository;

import com.demo.book.entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDetailRepository extends JpaRepository<BillDetail,Long> {
}
