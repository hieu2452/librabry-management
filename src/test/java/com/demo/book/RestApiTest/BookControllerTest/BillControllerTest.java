package com.demo.book.RestApiTest.BookControllerTest;

import com.demo.book.controller.BillController;
import com.demo.book.domain.dto.BillDetailDto;
import com.demo.book.domain.dto.BillDto;
import com.demo.book.domain.response.BorrowResponse;
import com.demo.book.domain.response.MessageResponse;
import com.demo.book.entity.Bill;
import com.demo.book.entity.BillDetail;
import com.demo.book.entity.Book;
import com.demo.book.entity.enums.BillStatus;
import com.demo.book.entity.enums.BorrowedBookStatus;
import com.demo.book.service.impl.BillServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.demo.book.RestApiTest.BookControllerTest.BookControllerTest.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = BillController.class,excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class BillControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BillServiceImpl billService;


    @Test
    public void shouldCreateBill() throws Exception {
        BillDto billDto = new BillDto();
        billDto.setUserId(1);
        billDto.setBooks(Arrays.asList(new BillDetailDto(3,2),new BillDetailDto(2,1)));

        when(billService.createBill(billDto)).thenReturn(new MessageResponse("Borrow Successfully"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bill/create")
                        .content(asJsonString(billDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    public void shouldReturnBills() throws Exception {
        List<Bill> bills = Arrays.asList(
                new Bill(1, BillStatus.BORROWED),
                new Bill(2, BillStatus.BORROWED),
                new Bill(3, BillStatus.BORROWED),
                new Bill(4, BillStatus.BORROWED)
        );

        when(billService.findAll()).thenReturn(bills);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/bill"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldReturnBillDetailsByBillId() throws Exception {
        long billId = 1L;
        List<BorrowResponse> billDetails = Arrays.asList(
                new BorrowResponse(LocalDateTime.now(),1, BorrowedBookStatus.BORROWED,1,"test1"),
                new BorrowResponse(LocalDateTime.now(),1, BorrowedBookStatus.BORROWED,1,"test2")
        );

        when(billService.findBillDetail(any(Long.class))).thenReturn(billDetails);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/bill/detail/{id}",billId))
                .andExpect(status().isOk())
                .andDo(print());
    }



}
