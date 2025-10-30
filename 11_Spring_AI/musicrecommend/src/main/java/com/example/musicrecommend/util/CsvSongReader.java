package com.example.musicrecommend.util;

import com.example.musicrecommend.dto.BulkSongRequest;
import org.springframework.stereotype.Component;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple CSV reader for demo. CSV header:
 * title,artist,album,genre,lyrics,description,releaseYear
 */
@Component
public class CsvSongReader {

    public List<BulkSongRequest.SongRequest> read(String resourcePath) {
        try {
            ClassPathResource resource = new ClassPathResource(resourcePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            String header = br.readLine(); // skip header
            List<BulkSongRequest.SongRequest> list = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] cols = line.split(",", -1);
                BulkSongRequest.SongRequest r = new BulkSongRequest.SongRequest();
                r.setTitle(cols.length > 0 ? cols[0] : "");
                r.setArtist(cols.length > 1 ? cols[1] : "");
                r.setAlbum(cols.length > 2 ? cols[2] : "");
                r.setGenre(cols.length > 3 ? cols[3] : "");
                r.setLyrics(cols.length > 4 ? cols[4] : "");
                r.setDescription(cols.length > 5 ? cols[5] : "");
                if (cols.length > 6 && !cols[6].isBlank()) {
                    try { r.setReleaseYear(Integer.parseInt(cols[6])); } catch (NumberFormatException ignored) {}
                }
                list.add(r);
            }
            br.close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Failed to read CSV resource: " + resourcePath, e);
        }
    }
}
