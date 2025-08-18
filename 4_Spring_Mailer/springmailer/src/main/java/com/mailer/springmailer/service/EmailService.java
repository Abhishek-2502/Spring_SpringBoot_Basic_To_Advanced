package com.mailer.springmailer.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String fromEmail;

    // Send simple text email
    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(fromEmail);
        mailSender.send(message);
    }

    // Send HTML email
    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        helper.setFrom(fromEmail);

        mailSender.send(mimeMessage);
    }

    // Send email with attachment
    public void sendEmailWithAttachment(String to, String subject, String text, File attachment) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        helper.setFrom(fromEmail);

        helper.addAttachment(attachment.getName(), attachment);

        mailSender.send(mimeMessage);
    }

    // Send HTML email with attachment
    public void sendHtmlEmailWithAttachment(String to, String subject, String htmlContent, File attachment) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true → HTML
        helper.setFrom(fromEmail);

        helper.addAttachment(attachment.getName(), attachment);

        mailSender.send(mimeMessage);
    }
}
