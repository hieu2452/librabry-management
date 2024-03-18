package com.demo.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @GetMapping("send")
    public String sendMessage(@RequestParam(defaultValue = "") String message) {
        kafkaTemplate.send("car-2", message);

        return message;
    }

    @GetMapping("send-key")
    public String sendMessageNoKey(@RequestParam(defaultValue = "") String message,@RequestParam(defaultValue = "") String key) {
        kafkaTemplate.send("car-2", 3,key,message);

        return message;
    }

}
