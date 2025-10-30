package com.example.musicrecommend.service;

import com.example.musicrecommend.dto.BulkSongRequest;
import com.example.musicrecommend.dto.BulkSongResponse;
import com.example.musicrecommend.model.Song;
import com.example.musicrecommend.repository.SongRepository;
import com.example.musicrecommend.repository.SongEmbeddingRepository;
import com.example.musicrecommend.util.CsvSongReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SongEmbeddingService {

    private final SongRepository songRepository;
    private final SongEmbeddingRepository embeddingRepository;
    private final VectorStore vectorStore;
    private final CsvSongReader csvSongReader;
    private final Logger log = LoggerFactory.getLogger(SongEmbeddingService.class);

    @Autowired
    public SongEmbeddingService(SongRepository songRepository,
                                SongEmbeddingRepository embeddingRepository,
                                VectorStore vectorStore,
                                CsvSongReader csvSongReader) {
        this.songRepository = songRepository;
        this.embeddingRepository = embeddingRepository;
        this.vectorStore = vectorStore;
        this.csvSongReader = csvSongReader;
    }

    @Transactional
    public BulkSongResponse createEmbeddingFromBulkSong(BulkSongRequest request) {
        if (request == null || request.getSongs() == null || request.getSongs().isEmpty()) {
            throw new IllegalArgumentException("Song request list cannot be null or empty");
        }
        log.info("Starting bulk embedding for {} songs", request.getSongs().size());

        // Map requests to Song
        List<Song> savedSongs = request.getSongs().stream()
                .map(req -> {
                    Song s = new Song();
                    s.setTitle(req.getTitle());
                    s.setArtist(req.getArtist());
                    s.setAlbum(req.getAlbum());
                    s.setGenre(req.getGenre());
                    s.setLyrics(req.getLyrics());
                    s.setDescription(req.getDescription());
                    s.setReleaseYear(req.getReleaseYear());
                    return s;
                }).collect(Collectors.toList());

        savedSongs = songRepository.saveAll(savedSongs);

        // Build documents for vector store
        List<Document> documents = savedSongs.stream()
                .map(this::createDocumentFromSong)
                .collect(Collectors.toList());

        // Add documents to vector store (this triggers embedding generation by Spring AI + OpenAI)
        embedDocuments(documents);

        // Optionally also persist SongEmbedding documents for auditing / faster queries
        // For demo, skip explicit embedding repository write because VectorStore + Mongo Atlas store handles it
        log.info("Successfully embedded {} songs", documents.size());
        return new BulkSongResponse(documents.size());
    }

    private Document createDocumentFromSong(Song song) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("title", song.getTitle());
        metadata.put("artist", song.getArtist());
        metadata.put("album", song.getAlbum());
        metadata.put("genre", song.getGenre());
        metadata.put("songId", song.getId());
        metadata.put("releaseYear", song.getReleaseYear());

        String content = String.format("Title: %s\nArtist: %s\nLyrics: %s", song.getTitle(), song.getArtist(),
                Optional.ofNullable(song.getLyrics()).orElse(""));

        // Document builder depends on Spring AI
        Document doc = Document.builder()
                .text(content)
                .metadata(metadata)
                .build();

        return doc;
    }

    private void embedDocuments(List<Document> documents) {
        try {
            vectorStore.add(documents); // vectorStore implementation handles embedding using configured provider
            log.debug("Successfully embedded {} documents", documents.size());
        } catch (Exception e) {
            log.error("Failed to embed documents", e);
            throw new RuntimeException("Vector embedding failed", e);
        }
    }

    // Utility: read from CSV file path in resources (optional helper used by controller)
    public BulkSongResponse createEmbeddingFromCsv(String resourcePath) {
        List<BulkSongRequest.SongRequest> songs = csvSongReader.read(resourcePath);
        BulkSongRequest req = new BulkSongRequest();
        req.setSongs(songs);
        return createEmbeddingFromBulkSong(req);
    }
}
