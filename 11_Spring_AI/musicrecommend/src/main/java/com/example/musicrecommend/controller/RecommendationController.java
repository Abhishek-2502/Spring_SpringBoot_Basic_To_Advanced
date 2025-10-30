package com.example.musicrecommend.controller;

import com.example.musicrecommend.dto.MusicRequest;
import com.example.musicrecommend.dto.SongRecommendationResponse;
import com.example.musicrecommend.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lyricmind/v1/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @PostMapping
    public ResponseEntity<List<SongRecommendationResponse>> recommendSongs(@RequestBody MusicRequest request) {
        int limit = request.getLimit() != null ? request.getLimit() : 10;
        List<SongRecommendationResponse> recommendations = recommendationService.recommendSongs(
                request.getMood(), limit);
        return ResponseEntity.ok(recommendations);
    }
}