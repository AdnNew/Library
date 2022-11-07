package com.library.notification.service;

import com.library.notification.model.Notification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitService {

    private final EmailSender emailSender;

    public RabbitService(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @RabbitListener(queues = "FirstQueue")
    void returnBookHandler(Notification notification){
        emailSender.sendEmails(notification);
    }

}
