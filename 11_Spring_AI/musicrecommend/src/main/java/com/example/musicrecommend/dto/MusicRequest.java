package com.example.musicrecommend.dto;

import lombok.Value;

@Value
public class MusicRequest {
    String mood;
    Integer limit;
}
