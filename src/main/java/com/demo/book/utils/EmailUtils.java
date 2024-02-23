package com.demo.book.utils;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailUtils {
//    @Autowired
//    private JavaMailSender emailSender;
    @Async
    public void sendEmail(String recipientEmail, String detail) {
        System.out.println("Email sent with invoice attachment successfully.");
//        try {
//            MimeMessage message = emailSender.createMimeMessage();
//
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            helper.setTo(recipientEmail);
//
//            helper.setSubject("Thank you for using our services");
//
//            helper.setText(detail);
//
//
//            emailSender.send(message);
//
//            System.out.println("Email sent with invoice attachment successfully.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
