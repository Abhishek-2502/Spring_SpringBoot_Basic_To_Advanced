package com.example.models.dto;

import com.example.models.model.GeminiModel;

import java.util.List;

public record ModelListResponse(String object, List<GeminiModel> data) {
}
