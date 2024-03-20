package com.demo.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @GetMapping("send")
    public String sendMessage(@RequestParam(defaultValue = "") String message) {
        kafkaTemplate.send("my-topic", message);

        return message;
    }

    @PostMapping("send-key")
    public String sendMessageNoKey(@RequestBody Map<String,String> request) {

        String message = request.get("message");
        String key = request.get("key");

        kafkaTemplate.send("my-topic",key,message);

        return message;
    }

}
