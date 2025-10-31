package com.example.rag.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * AppConfig - Configures ChatClient based on the active profile.
 * If the openai profile is active, ChatClient for OpenAI is created.
 * If the ollama profile is active, ChatClient for Ollama is created.
 */
@Configuration
public class AppConfig {

    @Bean
    @Profile("openai")
    public ChatClient openAIChatClient(OpenAiChatModel chatModel, VectorStore vectorStore) {
        return ChatClient.builder(chatModel)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()) // RAG advisor
                )
                .build();
    }

    @Bean
    @Profile("ollama")
    public ChatClient ollamaChatClient(OllamaChatModel chatModel, VectorStore vectorStore) {
        return ChatClient.builder(chatModel)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()) // RAG advisor
                )
                .build();
    }
}