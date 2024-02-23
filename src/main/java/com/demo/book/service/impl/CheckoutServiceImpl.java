package com.demo.book.service.impl;

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
import com.demo.book.repository.*;
import com.demo.book.service.CheckoutService;
import com.demo.book.utils.EmailUtils;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    private final Logger logger = LoggerFactory.getLogger(CheckoutServiceImpl.class);
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CheckoutRepository checkoutRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CheckoutDetailRepository checkoutDetailRepository;
    @Autowired
    private EmailUtils emailUtils;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Transactional
    @Override
    public MessageResponse createBill(CheckoutDto checkoutDto) {
        Member user = memberRepository.findById(checkoutDto.getUserId())
                .orElseThrow( ()-> new UserNotFoundException(checkoutDto.getUserId()));

        LibraryCard libraryCard = user.getLibraryCard();
        if(libraryCard == null) throw new LibraryCardNotFound("");

        if(checkoutDto.getBooks().isEmpty()) {
            throw new IllegalArgumentException("Invalid book info");
        }

        int total = 0;
        for (CheckoutDetailDto book : checkoutDto.getBooks()) {
            if(book.getQuantity()<=0){
                throw new IllegalArgumentException("Invalid quantity for book");
            }
            total += book.getQuantity();
        }

        if(libraryCard.getBookAvailable() < total || libraryCard.getStatus().equalsIgnoreCase("EXPIRED")) {
                throw new BorrowException("Unable to borrow book ");
        }

        com.demo.book.entity.Checkout checkOut = new com.demo.book.entity.Checkout();
        checkOut.setMember(user);
        checkOut.setStatus(BillStatus.BORROWED);
        for(CheckoutDetailDto book  : checkoutDto.getBooks()){

            CheckoutDetailKey checkOutDetailKey = new CheckoutDetailKey();
            Book book1 = bookRepository.findById(book.getBookId())
                    .orElseThrow(()-> new BookNotFoundException(book.getBookId()));
            if (book1.getQuantity() < book.getQuantity()) throw new BorrowException("Insufficient amount (Book: " + book1.getTitle()+")");
            book1.setQuantity(book1.getQuantity() - book.getQuantity());
            CheckoutDetail checkOutDetail = new CheckoutDetail(checkOutDetailKey,book.getQuantity(), BorrowedBookStatus.BORROWED, checkOut,book1);
            checkOut.getCheckoutDetails().add(checkOutDetail);
        }
        try {
            checkoutRepository.save(checkOut);
            if(user.getEmail() != null)
                emailUtils.sendEmail(user.getEmail(),"Borrow book");
            publisher.publishEvent(
                    new NotificationEvent(this,"User : " + user.getFullName() + " - id : " + user.getId()+" borrowed book"));
            return new MessageResponse("Borrow successfully");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("");
        }
    }

    @Override
    public List<com.demo.book.entity.Checkout> findAll() {
        return checkoutRepository.findAll(Sort.by(Sort.Direction.DESC,"createdDate"));
    }

    @Override
    public List<BorrowResponse> findBillDetail(long billId) {
        return checkoutDetailRepository.findByCheckoutId(billId).stream()
                .map(b -> new BorrowResponse(b.getBorrowedDate(),b.getQuantity(),b.getStatus(),b.getBook().getId(),b.getBook().getTitle()))
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public void returnBook(List<Long> bookIds, long billId) {
        List<CheckoutDetail> checkoutDetails = checkoutDetailRepository.findByCheckoutId(billId);
        if(checkoutDetails == null || checkoutDetails.isEmpty()) throw new IllegalArgumentException();
        boolean bookExist = true;
        for(CheckoutDetail checkOutDetail : checkoutDetails) {
            if(containBook(bookIds, checkOutDetail.getCheckOutDetailKey().getBookId())
                    && checkOutDetail.getStatus() == BorrowedBookStatus.BORROWED) {
                checkOutDetail.getBook().setQuantity(checkOutDetail.getQuantity()+ checkOutDetail.getBook().getQuantity());
                checkOutDetail.setStatus(BorrowedBookStatus.RETURNED);
                checkOutDetail.setReturnedDate(LocalDateTime.now());
                continue;
            }
            bookExist = false;
        }

        if (!bookExist) throw new BookNotFoundException("Bill id: " + billId + " books not found");
    }

    public boolean containBook(List<Long> bookIds, long bookId) {
        for(Long id: bookIds) {
            if(id == bookId) return true;
        }
        return false;
    }

}
