package com.example.models;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ModelsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModelsApplication.class, args);
	}
}


/*
1. Comment following things if you want to use the Gemini directly:
    -- OpenAI, Anthropic and Ollama dependency in pom.xml as they are not supported in 1.1.0-M2
    -- OpenAIController.java, AnthropicController.java and OllamaController.java
2. Change <spring-ai.version>1.0.0-M6</spring-ai.version> to <spring-ai.version>1.1.0-M2</spring-ai.version> in pom.xml
3. Put relevant keys in application.properties
 */

/*
1. Comment following things if you want to use the OpenAI, Ollama and Anthropic:
    -- Gemini dependency in pom.xml as they are not supported in 1.0.0-M6
    -- GeminiController.java
2. Change <spring-ai.version>1.1.0-M2</spring-ai.version> to  <spring-ai.version>1.0.0-M6</spring-ai.version> in pom.xml
3. Put relevant keys in application.properties
 */

