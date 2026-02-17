package com.example.ModeConnect.service.implementation.mail;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class EmailNotificationService {

    private final JavaMailSender mailSender;

    public  void sendEmailToCreator(String to, String subject, String content){
        SimpleMailMessage message =new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom("noreplay@modconnect.com");

        mailSender.send(message);

    }
}
