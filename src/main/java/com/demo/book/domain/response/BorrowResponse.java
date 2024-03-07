package com.demo.book.domain.response;

import com.demo.book.entity.enums.BillStatus;
import com.demo.book.entity.enums.BorrowedBookStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BorrowResponse {
    private LocalDateTime borrowedDate;
    private LocalDateTime returnedDate;
    private int quantity;
    private BorrowedBookStatus bookStatus;
    private long bookId;
    private String bookTitle;

    public BorrowResponse(LocalDateTime borrowedDate,LocalDateTime returnedDate ,int quantity, BorrowedBookStatus bookStatus, long bookId, String bookTitle) {
        this.borrowedDate = borrowedDate;
        this.returnedDate = returnedDate;
        this.quantity = quantity;
        this.bookStatus = bookStatus;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
    }
}
