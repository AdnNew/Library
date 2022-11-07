package com.library.notification.service;

import com.library.notification.model.Email;
import com.library.notification.model.Notification;

import javax.mail.MessagingException;

public interface EmailSender {

    void sendEmails(Notification notification);

    void sendEmail(Email email) throws MessagingException;
}
