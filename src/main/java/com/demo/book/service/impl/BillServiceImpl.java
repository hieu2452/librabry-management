package com.demo.book.service.impl;

import com.demo.book.dto.BillDetailDto;
import com.demo.book.dto.BillDto;
import com.demo.book.dto.BookDto;
import com.demo.book.entity.*;
import com.demo.book.entity.enums.BorrowedBookStatus;
import com.demo.book.event.notification.NotificationEvent;
import com.demo.book.exception.BorrowException;
import com.demo.book.exception.UserNotFoundException;
import com.demo.book.repository.*;
import com.demo.book.service.BillService;
import com.demo.book.utils.EmailUtils;
import jakarta.transaction.Transactional;
import org.apache.catalina.core.ApplicationPushBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private LibraryCardRepository libraryCardRepository;
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
    public String createBill(BillDto billDto) {
        Member user = memberRepository.findById(billDto.getUserId())
                .orElseThrow( ()-> new UserNotFoundException(billDto.getUserId()));

        LibraryCard libraryCard = libraryCardRepository.findByUserId(user.getId());

        int total = 0;
        for (BillDetailDto book : billDto.getBooks()) {
            total += book.getQuantity();
        }

        if(libraryCard.getBookAvailable() < total || libraryCard.getStatus().equalsIgnoreCase("EXPIRED")) {
            throw new BorrowException("Unable to borrow book ");
        }

        Bill bill = new Bill();
        bill.setUser(user);
        Bill newBill = billRepository.saveAndFlush(bill);

        for(BillDetailDto book  : billDto.getBooks()){

            BillDetailKey billDetailKey = new BillDetailKey(newBill.getId(),book.getBookId());
            Book book1 = bookRepository.findById(book.getBookId())
                    .orElseThrow();
            if (book1.getQuantity() < book.getQuantity()) throw new BorrowException("Insufficient amount (Book: " + book1.getTitle()+")");
            book1.setQuantity(book1.getQuantity()-book.getQuantity());
            BillDetail billDetail = new BillDetail(billDetailKey,book.getQuantity(), BorrowedBookStatus.BORROWED,newBill,book1);
            billDetailRepository.save(billDetail);
        }
        if(user.getEmail() != null)
            emailUtils.sendEmail(user.getEmail(),"Borrow book");

        publisher.publishEvent(
                new NotificationEvent(this,"User : " + user.getFullName() + " - id : " + user.getId()+" borrowed book"));
        return "Borrow successfully";
    }
}
