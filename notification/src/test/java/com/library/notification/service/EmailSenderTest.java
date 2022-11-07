package com.library.notification.service;

import com.library.notification.model.Email;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

@SpringBootTest
public class EmailSenderTest {

    @Autowired
    EmailSender emailSender;

    @Test
    void send_email_test() throws MessagingException {
        Email email = Email.builder()
                .to("to")
                .title("title")
                .content("content")
                .build();
        emailSender.sendEmail(email);
    }
}
