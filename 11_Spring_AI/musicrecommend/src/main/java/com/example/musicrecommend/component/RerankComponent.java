package com.example.musicrecommend.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class RerankComponent {

    private final ChatClient chatClient;
    private final Logger logger = LoggerFactory.getLogger(RerankComponent.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public RerankComponent(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public List<Document> rerank(String mood, List<Document> docs) {
        logger.info("Re-ranking {} documents for mood: '{}'", docs.size(), mood);

        // limit docs to avoid high token count
        List<Document> docsToRerank = docs.stream().limit(20).collect(Collectors.toList());

        String prompt = buildRerankingPrompt(mood, docsToRerank);

        // ✅ new Spring AI way
        String content = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        try {
            List<Map<String, Object>> ranking = objectMapper.readValue(
                    content,
                    new TypeReference<List<Map<String, Object>>>() {}
            );

            Map<Integer, Double> indexToScore = new HashMap<>();
            for (Map<String, Object> entry : ranking) {
                Integer idx = ((Number) entry.get("doc_index")).intValue();
                Double score = ((Number) entry.get("score")).doubleValue();
                indexToScore.put(idx, score);
            }

            List<Integer> sortedIdx = indexToScore.entrySet().stream()
                    .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                    .map(Map.Entry::getKey)
                    .toList();

            List<Document> reranked = new ArrayList<>();
            for (Integer i : sortedIdx) {
                reranked.add(docsToRerank.get(i));
            }

            logger.info("Re-ranked {} docs", reranked.size());
            return reranked;

        } catch (Exception e) {
            logger.error("Failed to parse reranking response", e);
            return docsToRerank;
        }
    }

    private String buildRerankingPrompt(String mood, List<Document> docs) {
        StringBuilder docsText = new StringBuilder();
        int idx = 0;
        for (Document d : docs) {
            docsText.append(String.format("""
                Index: %d
                Title: %s
                Artist: %s
                Album: %s
                Genre: %s
                ContentSnippet: %s

                """,
                    idx++,
                    d.getMetadata().getOrDefault("title", ""),
                    d.getMetadata().getOrDefault("artist", ""),
                    d.getMetadata().getOrDefault("album", ""),
                    d.getMetadata().getOrDefault("genre", ""),
                    excerpt(d.getText(), 160)));
        }

        return String.format("""
                You are a music recommendation ranking assistant.

                Rank the following songs based on their semantic relevance to the requested mood.
                Consider artist, title, and genre relevance.

                Requested Mood: %s

                Songs to rank:
                %s

                Instructions:
                - Return ONLY a JSON array.
                - Include ALL documents.
                - Use fields: doc_index, score (0.0–1.0), motivation.
                - Example: [{"doc_index":0,"score":0.95,"motivation":"Upbeat tempo matches mood"}]
                """, mood, docsText);
    }

    private String excerpt(String content, int maxLen) {
        if (content == null) return "";
        return content.length() <= maxLen ? content : content.substring(0, maxLen) + "...";
    }
}
