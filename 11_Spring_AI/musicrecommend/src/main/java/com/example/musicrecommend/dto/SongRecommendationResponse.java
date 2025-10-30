package com.example.musicrecommend.dto;

import lombok.Data;

@Data
public class SongRecommendationResponse {
    private String title;
    private String artist;
    private String album;
    private String genre;
    private Integer releaseYear;
    private String motivation;
}
