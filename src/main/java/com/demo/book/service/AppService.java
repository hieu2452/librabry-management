package com.demo.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class AppService {
    private static AppService instance;
    @Autowired
    private AdminService adminService;
    @Autowired
    private BillService billService;
    @Autowired
    private BookService bookService;


    public static AppService getInstance() {
        if(instance == null) {
            instance = new AppService();
        }

        return instance;
    }


    public AdminService getAdminService() {
        System.out.println("app service + " + adminService.getClass());
        return adminService;
    }
    public BillService getBillService() {
        return billService;
    }
    public BookService getBookService() {
        return bookService;
    }


}
