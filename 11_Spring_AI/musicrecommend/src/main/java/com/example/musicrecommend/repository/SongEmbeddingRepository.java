package com.example.musicrecommend.repository;

import com.example.musicrecommend.model.SongEmbedding;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongEmbeddingRepository extends MongoRepository<SongEmbedding, String> {
}