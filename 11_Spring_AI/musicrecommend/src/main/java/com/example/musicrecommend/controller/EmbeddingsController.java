package com.example.musicrecommend.controller;

import com.example.musicrecommend.dto.BulkSongRequest;
import com.example.musicrecommend.dto.BulkSongResponse;
import com.example.musicrecommend.service.SongEmbeddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lyricmind/v1/embeddings")
public class EmbeddingsController {

    @Autowired
    private SongEmbeddingService songEmbeddingService;

    @PostMapping("/bulk-songs")
    public ResponseEntity<BulkSongResponse> createEmbeddingFromBulkSong(@RequestBody BulkSongRequest request) {
        BulkSongResponse response = songEmbeddingService.createEmbeddingFromBulkSong(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // helper endpoint: load a resource CSV in src/main/resources by name
    @PostMapping("/bulk-songs/from-csv")
    public ResponseEntity<BulkSongResponse> createFromCsv(@RequestParam String resource) {
        BulkSongResponse response = songEmbeddingService.createEmbeddingFromCsv(resource);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}