package com.library.notification.controller;

import com.library.notification.model.Email;
import com.library.notification.service.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@Slf4j
public class NotificationController {

    private final EmailSender emailSender;

    public NotificationController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @PostMapping("/emails")
    ResponseEntity<String> sendEmail(@RequestBody @Valid Email email){
        try {
            emailSender.sendEmail(email);
        } catch (MessagingException e) {
            log.error("Notyfikacja się nie wysłała z powodu: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Wiadomość do '" + email.getTo() + "' nie została wysłana");
        }
        return ResponseEntity.ok().body("Wiadomość wysłana poprawnie.");
    }
}
