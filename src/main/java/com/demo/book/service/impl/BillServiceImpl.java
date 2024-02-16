package com.demo.book.service.impl;

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
import com.demo.book.repository.*;
import com.demo.book.service.BillService;
import com.demo.book.utils.EmailUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {
    private final Logger logger = LoggerFactory.getLogger(BillServiceImpl.class);
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BillDetailRepository billDetailRepository;
    @Autowired
    private EmailUtils emailUtils;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Transactional
    @Override
    public MessageResponse createBill(BillDto billDto) {
        Member user = memberRepository.findById(billDto.getUserId())
                .orElseThrow( ()-> new UserNotFoundException(billDto.getUserId()));

        LibraryCard libraryCard = user.getLibraryCard();
        if(libraryCard == null) throw new LibraryCardNotFound("");

        if(billDto.getBooks().isEmpty()) {
            throw new IllegalArgumentException("Invalid book info");
        }

        int total = 0;
        for (BillDetailDto book : billDto.getBooks()) {
            if(book.getQuantity()<=0){
                throw new IllegalArgumentException("Invalid quantity for book");
            }
            total += book.getQuantity();
        }

        if(libraryCard.getBookAvailable() < total || libraryCard.getStatus().equalsIgnoreCase("EXPIRED")) {
                throw new BorrowException("Unable to borrow book ");
        }

        Bill bill = new Bill();
        bill.setUser(user);
        bill.setStatus(BillStatus.BORROWED);
        for(BillDetailDto book  : billDto.getBooks()){

            BillDetailKey billDetailKey = new BillDetailKey();
            Book book1 = bookRepository.findById(book.getBookId())
                    .orElseThrow(()-> new BookNotFoundException(book.getBookId()));
            if (book1.getQuantity() < book.getQuantity()) throw new BorrowException("Insufficient amount (Book: " + book1.getTitle()+")");
            book1.setQuantity(book1.getQuantity()-book.getQuantity());
            BillDetail billDetail = new BillDetail(billDetailKey,book.getQuantity(), BorrowedBookStatus.BORROWED,bill,book1);
            bill.getBillDetails().add(billDetail);
        }
        try {
            billRepository.save(bill);
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
    public List<Bill> findAll() {
        return billRepository.findAll(Sort.by(Sort.Direction.DESC,"createdDate"));
    }

    @Override
    public List<BorrowResponse> findBillDetail(long billId) {
        return billDetailRepository.findByBillId(billId).stream()
                .map(b -> new BorrowResponse(b.getBorrowedDate(),b.getQuantity(),b.getStatus(),b.getBook().getId(),b.getBook().getTitle()))
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public void returnBook(List<Long> bookIds, long billId) {
        List<BillDetail> billDetails = billDetailRepository.findByBillId(billId);
        boolean bookExist = false;
        for(BillDetail billDetail : billDetails) {
            if(containBook(bookIds,billDetail.getBillDetailKey().getBookId())
                    && billDetail.getStatus() == BorrowedBookStatus.BORROWED) {
                billDetail.getBook().setQuantity(billDetail.getQuantity()+billDetail.getBook().getQuantity());
                billDetail.setStatus(BorrowedBookStatus.RETURNED);
                billDetail.setReturnedDate(LocalDateTime.now());
                bookExist = true;
            }
        }

        if (!bookExist) throw new BookNotFoundException("Bill id: " + billId + " books not found");
    }

    private boolean containBook(List<Long> bookIds, long bookId) {
        for(Long id: bookIds) {
            if(id == bookId) return true;
        }
        return false;
    }

}
