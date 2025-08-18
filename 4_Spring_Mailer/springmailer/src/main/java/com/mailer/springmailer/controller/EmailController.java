package com.mailer.springmailer.controller;

import com.mailer.springmailer.model.AttachmentEmailRequest;
import com.mailer.springmailer.model.AttachmentHtmlEmailRequest;
import com.mailer.springmailer.model.EmailRequest;
import com.mailer.springmailer.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api/mail")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    // 1. Send plain text email
    @PostMapping("/send")
    public String sendEmail(@Validated @RequestBody EmailRequest request) {
        emailService.sendSimpleEmail(request.getTo(), request.getSubject(), request.getMessage());
        return "Email sent successfully to " + request.getTo();
    }

    // 2. Send HTML email
    @PostMapping("/send-html")
    public String sendHtmlEmail(@Validated @RequestBody EmailRequest request) throws MessagingException {
        emailService.sendHtmlEmail(request.getTo(), request.getSubject(), request.getMessage());
        return "HTML Email sent successfully to " + request.getTo();
    }

    // 3. Send email with attachment
    @PostMapping("/send-attachment")
    public String sendEmailWithAttachment(@Validated @RequestBody AttachmentEmailRequest request) throws MessagingException {
        File file = new File(request.getFilePath());
        if (!file.exists()) {
            return "File not found at " + request.getFilePath();
        }
        emailService.sendEmailWithAttachment(request.getTo(), request.getSubject(), request.getMessage(), file);
        return "Email with attachment sent successfully to " + request.getTo();
    }

    // 4. Send HTML email with attachment
    @PostMapping("/send-html-attachment")
    public String sendHtmlEmailWithAttachment(@Validated @RequestBody AttachmentHtmlEmailRequest request) throws MessagingException {
        File file = new File(request.getFilePath());
        if (!file.exists()) {
            return "File not found at " + request.getFilePath();
        }
        emailService.sendHtmlEmailWithAttachment(request.getTo(), request.getSubject(), request.getHtmlMessage(), file);
        return "HTML Email with attachment sent successfully to " + request.getTo();
    }

}
