package com.example.models;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ModelsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModelsApplication.class, args);
	}
/*
1. Uncomment CommandLineRunner to test Gemini model directly
2. Comment following things if you want to use the Gemini directly:
    -- Relevant application.properties settings which are not used for Gemini
    -- OpenAI, Anthropic and Ollama dependency in pom.xml
    -- OpenAIViaGeminiController.java, OpenAIController.java, AnthropicController.java and OllamaController.java
3. Change <spring-ai.version>1.0.0-M6</spring-ai.version> to <spring-ai.version>1.1.0-M2</spring-ai.version> in pom.xml
 */

//    @Bean
//    CommandLineRunner commandLineRunner(ChatClient.Builder builder) {
//        return args -> {
//            var client = builder.build();
//            var response = client.prompt("Tell me an interesting fact about Google")
//                    .call()
//                    .content();
//
//            System.out.println(response);
//        };
//    }

}
