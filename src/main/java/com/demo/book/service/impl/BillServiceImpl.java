package com.demo.book.service.impl;

import com.demo.book.dto.BillDto;
import com.demo.book.dto.BookDto;
import com.demo.book.entity.*;
import com.demo.book.repository.*;
import com.demo.book.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LibraryCardRepository libraryCardRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BillDetailRepository billDetailRepository;

    @Override
    public String createBill(BillDto billDto) {
        User user = userRepository.findById(billDto.getUserId()).orElse(null);

        LibraryCard libraryCard = libraryCardRepository.findByUserId(user.getId());

        int total = 0;
        for (BookDto book : billDto.getBooks()) {

            total += book.getQuantity();
        }

        if(libraryCard.getBookAvailable() < total) {
            return "Unable to borrow book ";
        }

        Bill bill = new Bill();
        bill.setUser(user);
        Bill newBill = billRepository.saveAndFlush(bill);

        for(BookDto book  : billDto.getBooks()){

            BillDetailKey billDetailKey = new BillDetailKey(newBill.getId(),book.getId());
            Book book1 = bookRepository.findById(book.getId())
                    .orElseThrow();
            BillDetail billDetail = new BillDetail(billDetailKey,newBill,book1,book.getQuantity());
            billDetailRepository.save(billDetail);
        }

        return "Borrow successfully";
    }
}
