package com.mailer.springmailer.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AttachmentHtmlEmailRequest {
    @Email
    @NotBlank
    private String to;

    @NotBlank
    private String subject;

    @NotBlank
    private String htmlMessage;  // HTML content

    @NotBlank
    private String filePath; // absolute path to file
}

