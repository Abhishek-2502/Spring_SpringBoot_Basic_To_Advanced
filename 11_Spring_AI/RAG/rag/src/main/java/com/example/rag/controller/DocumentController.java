package com.example.rag.controller;

import com.example.rag.service.DocumentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class DocumentController {

    private final DocumentService documentService;


    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public String uploadDocument(@RequestParam("fileToUpload") MultipartFile document) {
        documentService.loadDocument(document.getResource());

        return "redirect:/chat";
    }

    @GetMapping("/chat")
    public String loadChat() {
        return "chat";
    }
}