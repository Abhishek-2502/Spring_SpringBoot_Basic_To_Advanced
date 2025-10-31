package com.example.rag.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private final TokenTextSplitter tokenSplitter = new TokenTextSplitter();
    private final String PROMPT_TEMPLATE = """
            You are a helpful assistant. Use the following context to answer the question.
            If the answer is not contained within the context, respond with "I don't know".

            Context:
            {context}

            Question:
            {question}
            """;

    private final int top_n = 10;
    private final double similarity_threshold = 0.5;

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
        try {


            // Step 1: Retrieve the most relevant chunks from the vector store
            var results = vectorStore.similaritySearch(
                    SearchRequest.query(question)
                            .withTopK(top_n)        // retrieve top n similar chunks
                            .withSimilarityThreshold(similarity_threshold) // filter less relevant content
            );

            if (results.isEmpty()) {
                return "I couldn't find any relevant information in the document.";
            }

            // Step 2: Combine all the retrieved context text
            String context = results.stream()
                    .map(doc -> doc.getContent())
                    .reduce("", (a, b) -> a + "\n" + b);

            // Step 3: Fill in the prompt template
            String finalPrompt = PROMPT_TEMPLATE
                    .replace("{context}", context)
                    .replace("{question}", question);

            // Step 4: Send prompt to the LLM
            return chatClient
                    .prompt()
                    .user(finalPrompt)
                    .call()
                    .content();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error while processing your question: " + e.getMessage();
        }
    }
}