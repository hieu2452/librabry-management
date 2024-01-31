package com.demo.book.service;

import com.demo.book.entity.Bill;
import com.demo.book.entity.enums.BillStatus;
import com.demo.book.repository.BillDetailRepository;
import com.demo.book.repository.BillRepository;
import com.demo.book.repository.BookRepository;
import com.demo.book.repository.MemberRepository;
import com.demo.book.service.impl.BillServiceImpl;
import com.demo.book.utils.EmailUtils;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BillServiceImplTest {
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BillRepository billRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BillDetailRepository billDetailRepository;
    @Mock
    private EmailUtils emailUtils;
    @InjectMocks
    private BillServiceImpl billService;

    private List<Bill> bills;
    @BeforeEach
    public void init() {
        bills = Arrays.asList(
                new Bill(1, BillStatus.BORROWED),
                new Bill(2, BillStatus.BORROWED),
                new Bill(3, BillStatus.BORROWED),
                new Bill(4, BillStatus.BORROWED)
        );
    }

    @Test
    public void findAllBill_returnListOfBill() {

        when(billRepository.findAll(any(Sort.class))).thenReturn(bills);

        assertNotNull(billService.findAll());
    }

    @Test
    public void findAllBill_returnListOfBillNull() {

        when(billRepository.findAll(any(Sort.class))).thenReturn(null);

        assertNull(billService.findAll());
    }
}
