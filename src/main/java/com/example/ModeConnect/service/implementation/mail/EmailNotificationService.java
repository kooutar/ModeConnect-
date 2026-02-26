package com.example.ModeConnect.service.implementation.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.ModeConnect.Enums.OrderStatus;

import lombok.AllArgsConstructor;

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
        // 🔔 Notification accept / reject
    public void notifyUserOrderStatus(String toEmail, String orderTitle, OrderStatus status) {

        String subject = "Update on your order";
        String content;

        if (status == OrderStatus.ACCEPTED) {
            content = "Good news! 🎉\n\n"
                    + "Your order \"" + orderTitle + "\" has been ACCEPTED.";
        } else {
            content = "Hello,\n\n"
                    + "Unfortunately, your order \"" + orderTitle + "\" has been REJECTED.";
        }

        sendEmailToCreator(toEmail, subject, content);
    }
}
