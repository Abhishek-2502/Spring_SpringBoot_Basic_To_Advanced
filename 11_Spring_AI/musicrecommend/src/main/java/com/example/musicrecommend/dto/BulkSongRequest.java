package com.example.musicrecommend.dto;

import lombok.Data;

import java.util.List;

@Data
public class BulkSongRequest {
    private List<SongRequest> songs;

    @Data
    public static class SongRequest {
        private String title;
        private String artist;
        private String album;
        private String genre;
        private String lyrics;
        private String description;
        private Integer releaseYear;
    }
}
