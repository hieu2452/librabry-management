package com.demo.book.controller;

import com.demo.book.config.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private MessageProducer messageProducer;

    @GetMapping("send")
    public String sendMessage(@RequestParam(defaultValue = "") String message) {
        messageProducer.sendMessage("test-consumer-group", message);

        return message;
    }
}
