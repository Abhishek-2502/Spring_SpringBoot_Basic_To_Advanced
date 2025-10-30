/*
For OpenAI compatible models and for Gemini through the OpenAI API.
1. Comment following things if you want to use the Gemini directly:
    -- Relevant application.properties settings
    -- OpenAI dependency in pom.xml
    -- This Whole code
2. Change <spring-ai.version>1.0.0-M6</spring-ai.version> to <spring-ai.version>1.1.0-M2</spring-ai.version> in pom.xml
*/
//
//package dev.danvega.flash;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestClient;
//
//import java.util.List;
//
//@RestController
//public class GeminiModelController {
//
//    private static final Logger log = LoggerFactory.getLogger(GeminiModelController.class);
//    @Value("${spring.ai.openai.api-key}")
//    private String GEMINI_API_KEY;
//    private final RestClient restClient;
//
//    public GeminiModelController(RestClient.Builder builder, @Value("${spring.ai.openai.chat.base-url}") String baseUrl) {
//        log.info("GeminiModelController..." + baseUrl);
//        this.restClient = builder
//                .baseUrl(baseUrl)
//                .build();
//    }
//
//    /*
//        curl https://generativelanguage.googleapis.com/v1beta/openai/models \                                                                                                                                                                                                  ✔  10s   base 
//        -H "Authorization: Bearer GEMINI_API_KEY"
//     */
//    @GetMapping("/models")
//    public List<GeminiModel> models() {
//        ResponseEntity<ModelListResponse> response = restClient.get()
//                .uri("/v1beta/openai/models")
//                .header("Authorization", "Bearer " + GEMINI_API_KEY)
//                .retrieve()
//                .toEntity(ModelListResponse.class);
//        return response.getBody().data();
//    }
//
//}
