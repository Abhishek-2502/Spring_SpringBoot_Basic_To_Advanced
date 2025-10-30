package dev.danvega.flash;

import java.util.List;

public record ModelListResponse(String object, List<GeminiModel> data) {
}
