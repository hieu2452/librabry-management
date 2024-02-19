package com.demo.book.RestApiTest.BookControllerTest;

import com.demo.book.controller.CheckoutController;
import com.demo.book.domain.dto.CheckoutDetailDto;
import com.demo.book.domain.dto.CheckoutDto;
import com.demo.book.domain.response.BorrowResponse;
import com.demo.book.domain.response.MessageResponse;
import com.demo.book.entity.Checkout;
import com.demo.book.entity.enums.BillStatus;
import com.demo.book.entity.enums.BorrowedBookStatus;
import com.demo.book.service.impl.CheckoutServiceImpl;
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

@WebMvcTest(value = CheckoutController.class,excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class CheckoutServiceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CheckoutServiceImpl billService;


    @Test
    public void shouldCreateBill() throws Exception {
        CheckoutDto checkoutDto = new CheckoutDto();
        checkoutDto.setUserId(1);
        checkoutDto.setBooks(Arrays.asList(new CheckoutDetailDto(3,2),new CheckoutDetailDto(2,1)));

        when(billService.createBill(checkoutDto)).thenReturn(new MessageResponse("Borrow Successfully"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bill/create")
                        .content(asJsonString(checkoutDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    public void shouldReturnBills() throws Exception {
        List<Checkout> checkOuts = Arrays.asList(
                new Checkout(1, BillStatus.BORROWED),
                new Checkout(2, BillStatus.BORROWED),
                new Checkout(3, BillStatus.BORROWED),
                new Checkout(4, BillStatus.BORROWED)
        );

        when(billService.findAll()).thenReturn(checkOuts);

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
