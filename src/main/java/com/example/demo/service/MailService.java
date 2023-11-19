package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class MailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendTextEmail(String email, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@example.com");
            message.setTo(email);
            message.setSubject(subject);
            message.setText(body);
            emailSender.send(message);
        } catch (Exception e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
    
}
