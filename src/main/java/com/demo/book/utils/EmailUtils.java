package com.demo.book.utils;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailUtils {
    @Autowired
    private JavaMailSender emailSender;
    @Async
    public void sendInvoiceEmail(String recipientEmail, String detail) {
        try {
            // Create a MimeMessage
            MimeMessage message = emailSender.createMimeMessage();

            // Enable the multipart flag to attach the PDF
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set the recipient's email address
            helper.setTo(recipientEmail);

            // Set the email subject
            helper.setSubject("Thank you for using our services");

            // Set the email body
            helper.setText(detail);


            // Send the email
            emailSender.send(message);

            System.out.println("Email sent with invoice attachment successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
