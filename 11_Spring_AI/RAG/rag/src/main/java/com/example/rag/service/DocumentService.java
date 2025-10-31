package com.example.rag.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private final TokenTextSplitter tokenSplitter = new TokenTextSplitter();

    // Inject ChatClient and VectorStore via the constructor
    public DocumentService(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    public void loadDocument(Resource resource) {
        try {
            // Step 1: Read the PDF content
            var pdfReader = new PagePdfDocumentReader(resource, PdfDocumentReaderConfig.defaultConfig());

            // Step 2: Split the content into tokens
            var tokens = tokenSplitter.split(pdfReader.read());

            if (tokens.isEmpty()) {
                throw new IllegalStateException("No tokens were extracted from the PDF.");
            }

            // Step 3: Write tokens to the vector store
            vectorStore.write(tokens);

            // Log success for debugging
            System.out.println("Document successfully uploaded to vector store!");
            System.out.println("Tokens count: " + tokens.size());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load document: " + e.getMessage(), e);
        }
    }

    public String askQuestion(String question) {
        return chatClient
                .prompt()
                .user(question)
                .call()
                .content();
    }
}