package com.demo.book.UnitTest.service;

import com.demo.book.domain.dto.CheckoutDetailDto;
import com.demo.book.domain.dto.CheckoutDto;
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
import com.demo.book.repository.CheckoutDetailRepository;
import com.demo.book.repository.CheckoutRepository;
import com.demo.book.repository.BookRepository;
import com.demo.book.repository.MemberRepository;
import com.demo.book.service.impl.CheckoutServiceImpl;
import com.demo.book.utils.EmailUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CheckoutServiceServiceImplTest {
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private CheckoutRepository checkoutRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CheckoutDetailRepository checkoutDetailRepository;
    @Mock
    private EmailUtils emailUtils;
    @Mock
    private ApplicationEventPublisher publisher;
    @InjectMocks
    private CheckoutServiceImpl billService;

    private List<Checkout> checkOuts;
    private CheckoutDto checkoutDto;
    private Member member;
    private Checkout checkOut;
    @BeforeEach
    public void init() {
        checkOuts = Arrays.asList(
                new Checkout(1, BillStatus.BORROWED),
                new Checkout(2, BillStatus.BORROWED),
                new Checkout(3, BillStatus.BORROWED),
                new Checkout(4, BillStatus.BORROWED)
        );
        checkOut = new Checkout();

        checkoutDto = new CheckoutDto();
        checkoutDto.setUserId(1);
        checkoutDto.setBooks(Arrays.asList(new CheckoutDetailDto(3,2),new CheckoutDetailDto(2,1)));

        member = new Member();
        member.setEmail("abc@gmail.com");
        member.setLibraryCard(new LibraryCard("AVAILABLE",5));
    }

    @Test
    public void findAllBill_returnListOfBill() {

        when(checkoutRepository.findAll(any(Sort.class))).thenReturn(checkOuts);

        billService.findAll();

        verify(checkoutRepository).findAll(any(Sort.class));
    }
    @Test
    public void createBill_ReturnSuccess() {

        List<Book> books = Arrays.asList(
                new Book.Builder("book","book",4).id(3).build(),
                new Book.Builder("book","book",4).id(2).build());

        when(memberRepository.findById(checkoutDto.getUserId())).thenReturn(Optional.of(member));
        when(checkoutRepository.save(any(Checkout.class))).thenReturn(checkOut);

        for(Book book:books) {
            when(bookRepository.findById(eq(book.getId()))).thenReturn(Optional.of(book));
        }

        MessageResponse result = billService.createBill(checkoutDto);

        assertEquals("Borrow successfully",result.getMessage());

        verify(memberRepository, times(1)).findById(any(Long.class));
        verify(bookRepository, times(checkoutDto.getBooks().size())).findById(anyLong());
        verify(checkoutRepository, times(1)).save(any(Checkout.class));
        verify(publisher, atLeastOnce()).publishEvent(any(NotificationEvent.class));

    }

    @Test
    public void createBillNonExistUser_ReturnNotFound() {

        when(memberRepository.findById(checkoutDto.getUserId())).thenReturn(Optional.empty());

        assertThrowsExactly(UserNotFoundException.class,() -> billService.createBill(checkoutDto));
    }

    @Test
    public void createBillNonExistCard_ReturnNotFound() {
        long userId = 1;
        Member member = new Member();
        member.setEmail("abc@gmail.com");

        when(memberRepository.findById(userId)).thenReturn(Optional.of(member));

        assertThrowsExactly(LibraryCardNotFound.class,() -> billService.createBill(checkoutDto));
    }


    @Test
    public void createBillEmptyBookInfo_ThrowException() {
        checkoutDto.setBooks(new ArrayList<>());

        when(memberRepository.findById(checkoutDto.getUserId())).thenReturn(Optional.of(member));

        assertThrowsExactly(IllegalArgumentException.class,() -> billService.createBill(checkoutDto));
    }

    @Test
    public void createBillEmptyInvalidBookQuantity_ThrowException() {
        checkoutDto.setBooks(Arrays.asList(new CheckoutDetailDto(3,0),new CheckoutDetailDto(2,0)));

        when(memberRepository.findById(checkoutDto.getUserId())).thenReturn(Optional.of(member));

        assertThrowsExactly(IllegalArgumentException.class,() -> billService.createBill(checkoutDto));
    }

    @Test
    public void createBillCardExpired_ThrowException() {

        member.getLibraryCard().setStatus("EXPIRED");

        when(memberRepository.findById(checkoutDto.getUserId())).thenReturn(Optional.of(member));


        assertThrowsExactly(BorrowException.class,() -> billService.createBill(checkoutDto));
    }

    @Test
    public void createBillCardBookLimit_ThrowException() {

        member.getLibraryCard().setBookAvailable(0);

        when(memberRepository.findById(checkoutDto.getUserId())).thenReturn(Optional.of(member));


        assertThrowsExactly(BorrowException.class,() -> billService.createBill(checkoutDto));
    }

    @Test
    public void createBillInsufficientBook_ThrowException() {
        checkoutDto.setBooks(Arrays.asList(new CheckoutDetailDto(3,2),new CheckoutDetailDto(2,1)));
        List<Book> books = Arrays.asList(
                new Book.Builder("book","book",1).id(3).build(),
                new Book.Builder("book","book",4).id(2).build());

        when(bookRepository.findById(any())).thenReturn(Optional.of(books.get(0))).thenReturn(Optional.of(books.get(1)));

        when(memberRepository.findById(checkoutDto.getUserId())).thenReturn(Optional.of(member));


        assertThrowsExactly(BorrowException.class,() -> billService.createBill(checkoutDto));
    }

    @Test
    public void createBillNonExistBook_ThrowException() {

        when(bookRepository.findById(any())).thenReturn(Optional.empty());

        when(memberRepository.findById(checkoutDto.getUserId())).thenReturn(Optional.of(member));


        assertThrowsExactly(BookNotFoundException.class,() -> billService.createBill(checkoutDto));
    }


    @Test
    public void createBillError_ThrowException() {
        List<Book> books = Arrays.asList(
                new Book.Builder("book","book",3).id(3).build(),
                new Book.Builder("book","book",4).id(2).build());

        when(bookRepository.findById(any())).thenReturn(Optional.empty());
        when(memberRepository.findById(checkoutDto.getUserId())).thenReturn(Optional.of(member));
        when(bookRepository.findById(any())).thenReturn(Optional.of(books.get(0))).thenReturn(Optional.of(books.get(1)));
        when(checkoutRepository.save(any(Checkout.class))).thenThrow(RuntimeException.class);

        assertThrowsExactly(RuntimeException.class,() -> billService.createBill(checkoutDto));
    }

    @Test
    public void findBillDetailNull() {
        long billId = 1L;

        when(checkoutDetailRepository.findByCheckoutId(billId)).thenReturn(new ArrayList<>());

        List<BorrowResponse> responses = billService.findBillDetail(billId);

        assertEquals(0,responses.size());
    }

    @Test
    public void findBillDetailSuccess() {
        long billId = 1L;
        CheckoutDetail checkoutDetail1 = new CheckoutDetail();
        checkoutDetail1.setStatus(BorrowedBookStatus.BORROWED);
        checkoutDetail1.setQuantity(1);
        checkoutDetail1.setBook( new Book.Builder("book","book",3).id(3).build());

        CheckoutDetail checkoutDetail2 = new CheckoutDetail();
        checkoutDetail2.setStatus(BorrowedBookStatus.BORROWED);
        checkoutDetail2.setQuantity(1);
        checkoutDetail2.setBook(new Book.Builder("book","book",3).id(3).build());

        List<CheckoutDetail> checkoutDetails = Arrays.asList(checkoutDetail1, checkoutDetail2);

        when(checkoutDetailRepository.findByCheckoutId(billId)).thenReturn(checkoutDetails);

        List<BorrowResponse> responses = billService.findBillDetail(billId);

        assertEquals(checkoutDetails.size(),responses.size());
    }
}
