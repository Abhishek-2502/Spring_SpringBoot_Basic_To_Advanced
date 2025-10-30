package com.example.musicrecommend.service;

import com.example.musicrecommend.dto.SongRecommendationResponse;
import com.example.musicrecommend.repository.SongRepository;
import com.example.musicrecommend.component.SemanticQueryComponent;
import com.example.musicrecommend.component.RerankComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class RecommendationService {

    private static final int MAX_LIMIT = 100;

    private final SemanticQueryComponent semanticQueryComponent;
    private final RerankComponent rerankComponent;
    private final SongRepository songRepository;
    private final Logger log = LoggerFactory.getLogger(RecommendationService.class);

    @Autowired
    public RecommendationService(SemanticQueryComponent semanticQueryComponent,
                                 RerankComponent rerankComponent,
                                 SongRepository songRepository) {
        this.semanticQueryComponent = semanticQueryComponent;
        this.rerankComponent = rerankComponent;
        this.songRepository = songRepository;
    }

    public List<SongRecommendationResponse> recommendSongs(String mood, int limit) {
        log.info("Requesting song recommendations for mood: '{}' with limit: {}", mood, limit);
        try {
            List<Document> candidates = findCandidateSongs(mood, limit);
            if (candidates.isEmpty()) {
                log.info("No candidate songs found for mood: '{}'", mood);
                return Collections.emptyList();
            }

            List<Document> reranked = rerankComponent.rerank(mood, candidates);
            List<SongRecommendationResponse> results = mapDocumentsToRecommendations(reranked, limit);
            log.info("Successfully generated {} recommendations for mood: '{}'", results.size(), mood);
            return results;
        } catch (Exception e) {
            log.error("Failed to generate recommendations for mood: '{}'", mood, e);
            throw new RuntimeException("Recommendation generation failed", e);
        }
    }

    private List<Document> findCandidateSongs(String mood, int limit) {
        try {
            int candidateLimit = Math.min(limit * 2, MAX_LIMIT);
            return semanticQueryComponent.similaritySearch(mood, candidateLimit);
        } catch (Exception e) {
            log.error("Failed to find candidate songs for mood: '{}'", mood, e);
            throw new RuntimeException("Candidate search failed", e);
        }
    }

    private List<SongRecommendationResponse> mapDocumentsToRecommendations(List<Document> docs, int limit) {
        List<SongRecommendationResponse> out = new ArrayList<>();
        int count = 0;
        for (Document d : docs) {
            if (count++ >= limit) break;
            Map<String, Object> md = d.getMetadata();
            SongRecommendationResponse resp = new SongRecommendationResponse();
            resp.setTitle((String) md.getOrDefault("title", ""));
            resp.setArtist((String) md.getOrDefault("artist", ""));
            resp.setAlbum((String) md.getOrDefault("album", ""));
            resp.setGenre((String) md.getOrDefault("genre", ""));
            Object ry = md.get("releaseYear");
            if (ry instanceof Number) resp.setReleaseYear(((Number) ry).intValue());
            resp.setMotivation((String) md.getOrDefault("motivation", "")); // if present by reranker
            out.add(resp);
        }
        return out;
    }
}
