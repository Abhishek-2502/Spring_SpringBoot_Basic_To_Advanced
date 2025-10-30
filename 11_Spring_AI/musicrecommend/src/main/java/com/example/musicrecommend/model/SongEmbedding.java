package com.example.musicrecommend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "song_embedding")
public class SongEmbedding {

    @Id
    private String id;
    private String songId;
    private String content;
    private List<Double> embedding;
    private Map<String,Object> metadata;

}