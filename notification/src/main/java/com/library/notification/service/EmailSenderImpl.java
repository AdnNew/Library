package com.library.notification.service;

import com.library.notification.model.Email;
import com.library.notification.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender javaMailSender;

    public EmailSenderImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmails(Notification notification) {
        String title = "Dziękujemy za zwrot książki " + notification.getTitle();
        StringBuilder content = contentBuild(notification);
        try {
            sendEmail(notification.getEmail(), title, content.toString());
        } catch (MessagingException e) {
            log.error("Notyfikacja się nie wysłała z powodu: " + e.getMessage());
        }
    }

    private static StringBuilder contentBuild(Notification notification) {
        StringBuilder content = new StringBuilder();
        content.append("Cześć " + notification.getFirstName() + ".");
        content.append("\n");
        content.append("Cieszymy się niezmiernie, że zwróciłeś książkę '" + notification.getTitle() + "'.");
        content.append("\n");
        content.append(" Mamy nadzieję, że przyjemnie Ci się ją czytało. Chcielibyśmy zachęcić Cie do podzielenia się swoją opinią na jej temat.");
        content.append("\n");
        content.append("Inni użytkownicy ocenili Twoją lekturę na: " + notification.getRating() + ".");
        content.append("\n");
        content.append("Zachęcamy Cie do również do kolejnego odwiedzenia nas i wypożyczenia swoich ulubionych książek.");
        content.append("\n");
        content.append("Pozdrawiamy");
        return content;
    }

    @Override
    public void sendEmail(Email email) throws MessagingException {
        sendEmail(email.getTo(), email.getTitle(), email.getContent());
    }

    private void sendEmail(String to, String title, String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(title);
        mimeMessageHelper.setText(content, false);
        javaMailSender.send(message);
    }
}
