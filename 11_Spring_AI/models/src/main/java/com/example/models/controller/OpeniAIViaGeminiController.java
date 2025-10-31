// For OpenAI compatible models and for Gemini through the OpenAI API.
// It uses RestClient to make HTTP requests to the Gemini API endpoint.
// Spring AI is not used here directly for model interaction.

package com.example.models.controller;

import com.example.models.dto.ModelListResponse;
import com.example.models.model.GeminiModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
public class OpeniAIViaGeminiController {

    private static final Logger log = LoggerFactory.getLogger(OpeniAIViaGeminiController.class);
    @Value("${spring.ai.openai.api-key}")
    private String GEMINI_API_KEY;
    private final RestClient restClient;

    public OpeniAIViaGeminiController(RestClient.Builder builder, @Value("${spring.ai.openai.chat.base-url}") String baseUrl) {
        log.info("GeminiModelController..." + baseUrl);
        this.restClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    /*
        curl https://generativelanguage.googleapis.com/v1beta/openai/models \                                                                                                                                                                                                  ✔  10s   base 
        -H "Authorization: Bearer GEMINI_API_KEY"
     */
    @GetMapping("/models")
    public List<GeminiModel> models() {
        ResponseEntity<ModelListResponse> response = restClient.get()
                .uri("/v1beta/openai/models")
                .header("Authorization", "Bearer " + GEMINI_API_KEY)
                .retrieve()
                .toEntity(ModelListResponse.class);
        return response.getBody().data();
    }

}
