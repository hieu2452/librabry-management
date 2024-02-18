package com.demo.book.UnitTest.service;

import com.demo.book.domain.dto.BillDetailDto;
import com.demo.book.domain.dto.BillDto;
import com.demo.book.domain.response.BorrowResponse;
import com.demo.book.domain.response.MessageResponse;
import com.demo.book.entity.*;
import com.demo.book.entity.enums.BillStatus;
import com.demo.book.entity.enums.BorrowedBookStatus;
import com.demo.book.event.notification.NotificationEvent;
import com.demo.book.exception.BookNotFoundException;
import com.demo.book.exception.BorrowException;
import com.demo.book.exception.LibraryCardNotFound;
import com.demo.book.exception.UserNotFoundException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    @Mock
    private ApplicationEventPublisher publisher;
    @InjectMocks
    private BillServiceImpl billService;

    private List<Bill> bills;
    private BillDto billDto;
    private Member member;
    private Bill bill;
    @BeforeEach
    public void init() {
        bills = Arrays.asList(
                new Bill(1, BillStatus.BORROWED),
                new Bill(2, BillStatus.BORROWED),
                new Bill(3, BillStatus.BORROWED),
                new Bill(4, BillStatus.BORROWED)
        );
        bill = new Bill();

        billDto = new BillDto();
        billDto.setUserId(1);
        billDto.setBooks(Arrays.asList(new BillDetailDto(3,2),new BillDetailDto(2,1)));

        member = new Member();
        member.setEmail("abc@gmail.com");
        member.setLibraryCard(new LibraryCard("AVAILABLE",5));
    }

    @Test
    public void findAllBill_returnListOfBill() {

        when(billRepository.findAll(any(Sort.class))).thenReturn(bills);

        billService.findAll();

        verify(billRepository).findAll(any(Sort.class));
    }
    @Test
    public void createBill_ReturnSuccess() {

        List<Book> books = Arrays.asList(
                new Book.Builder("book","book",4).id(3).build(),
                new Book.Builder("book","book",4).id(2).build());

        when(memberRepository.findById(billDto.getUserId())).thenReturn(Optional.of(member));
        when(billRepository.save(any(Bill.class))).thenReturn(bill);

        for(Book book:books) {
            when(bookRepository.findById(eq(book.getId()))).thenReturn(Optional.of(book));
        }

        MessageResponse result = billService.createBill(billDto);

        assertEquals("Borrow successfully",result.getMessage());

        verify(memberRepository, times(1)).findById(any(Long.class));
        verify(bookRepository, times(billDto.getBooks().size())).findById(anyLong());
        verify(billRepository, times(1)).save(any(Bill.class));
        verify(publisher, atLeastOnce()).publishEvent(any(NotificationEvent.class));

    }

    @Test
    public void createBillNonExistUser_ReturnNotFound() {

        when(memberRepository.findById(billDto.getUserId())).thenReturn(Optional.empty());

        assertThrowsExactly(UserNotFoundException.class,() -> billService.createBill(billDto));
    }

    @Test
    public void createBillNonExistCard_ReturnNotFound() {
        long userId = 1;
        Member member = new Member();
        member.setEmail("abc@gmail.com");

        when(memberRepository.findById(userId)).thenReturn(Optional.of(member));

        assertThrowsExactly(LibraryCardNotFound.class,() -> billService.createBill(billDto));
    }


    @Test
    public void createBillEmptyBookInfo_ThrowException() {
        billDto.setBooks(new ArrayList<>());

        when(memberRepository.findById(billDto.getUserId())).thenReturn(Optional.of(member));

        assertThrowsExactly(IllegalArgumentException.class,() -> billService.createBill(billDto));
    }

    @Test
    public void createBillEmptyInvalidBookQuantity_ThrowException() {
        billDto.setBooks(Arrays.asList(new BillDetailDto(3,0),new BillDetailDto(2,0)));

        when(memberRepository.findById(billDto.getUserId())).thenReturn(Optional.of(member));

        assertThrowsExactly(IllegalArgumentException.class,() -> billService.createBill(billDto));
    }

    @Test
    public void createBillCardExpired_ThrowException() {

        member.getLibraryCard().setStatus("EXPIRED");

        when(memberRepository.findById(billDto.getUserId())).thenReturn(Optional.of(member));


        assertThrowsExactly(BorrowException.class,() -> billService.createBill(billDto));
    }

    @Test
    public void createBillCardBookLimit_ThrowException() {

        member.getLibraryCard().setBookAvailable(0);

        when(memberRepository.findById(billDto.getUserId())).thenReturn(Optional.of(member));


        assertThrowsExactly(BorrowException.class,() -> billService.createBill(billDto));
    }

    @Test
    public void createBillInsufficientBook_ThrowException() {
        billDto.setBooks(Arrays.asList(new BillDetailDto(3,2),new BillDetailDto(2,1)));
        List<Book> books = Arrays.asList(
                new Book.Builder("book","book",1).id(3).build(),
                new Book.Builder("book","book",4).id(2).build());

        when(bookRepository.findById(any())).thenReturn(Optional.of(books.get(0))).thenReturn(Optional.of(books.get(1)));

        when(memberRepository.findById(billDto.getUserId())).thenReturn(Optional.of(member));


        assertThrowsExactly(BorrowException.class,() -> billService.createBill(billDto));
    }

    @Test
    public void createBillNonExistBook_ThrowException() {

        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        when(memberRepository.findById(billDto.getUserId())).thenReturn(Optional.of(member));


        assertThrowsExactly(BookNotFoundException.class,() -> billService.createBill(billDto));
    }


    @Test
    public void createBillError_ThrowException() {
        List<Book> books = Arrays.asList(
                new Book.Builder("book","book",3).id(3).build(),
                new Book.Builder("book","book",4).id(2).build());

        when(bookRepository.findById(any())).thenReturn(Optional.empty());
        when(memberRepository.findById(billDto.getUserId())).thenReturn(Optional.of(member));
        when(bookRepository.findById(any())).thenReturn(Optional.of(books.get(0))).thenReturn(Optional.of(books.get(1)));
        when(billRepository.save(any(Bill.class))).thenThrow(RuntimeException.class);

        assertThrowsExactly(RuntimeException.class,() -> billService.createBill(billDto));
    }

    @Test
    public void findBillDetailNull() {
        long billId = 1L;

        when(billDetailRepository.findByBillId(billId)).thenReturn(new ArrayList<>());

        List<BorrowResponse> responses = billService.findBillDetail(billId);

        assertEquals(0,responses.size());
    }

    @Test
    public void findBillDetailSuccess() {
        long billId = 1L;
        BillDetail billDetail1 = new BillDetail();
        billDetail1.setStatus(BorrowedBookStatus.BORROWED);
        billDetail1.setQuantity(1);
        billDetail1.setBook( new Book.Builder("book","book",3).id(3).build());

        BillDetail billDetail2 = new BillDetail();
        billDetail2.setStatus(BorrowedBookStatus.BORROWED);
        billDetail2.setQuantity(1);
        billDetail2.setBook(new Book.Builder("book","book",3).id(3).build());

        List<BillDetail> billDetails = Arrays.asList(billDetail1,billDetail2);

        when(billDetailRepository.findByBillId(billId)).thenReturn(billDetails);

        List<BorrowResponse> responses = billService.findBillDetail(billId);

        assertEquals(billDetails.size(),responses.size());
    }
}
