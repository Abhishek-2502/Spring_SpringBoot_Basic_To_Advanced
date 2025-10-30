# Hello Flash: Spring Boot + Gemini API with OpenAI Compatibility

![Spring Boot + Gemini](https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen)
![Spring AI](https://img.shields.io/badge/Spring%20AI-1.0.0--M6-blue)
![Java](https://img.shields.io/badge/Java-23-orange)

## Introduction

**Hello Flash** demonstrates how to leverage Google's Gemini models through Spring Boot using OpenAI-compatible endpoints. This project showcases the simplicity of connecting to Google's advanced AI models while maintaining compatibility with familiar OpenAI client libraries and patterns.

While Google plans to release an official Java SDK for Gemini in the future, this OpenAI compatibility layer provides an excellent workaround for Java developers. With just a few configuration changes, you can redirect your existing OpenAI-based applications to use Gemini's powerful language models using only an API key. This approach allows for seamless integration while taking advantage of Gemini's capabilities.

## Project Overview

This Spring Boot application provides:

1. A command-line example that queries Gemini for an interesting fact about Google
2. A REST endpoint to list available Gemini models
3. Configuration settings to use Spring AI with Gemini's OpenAI-compatible API

## Project Requirements

- Java 23
- Maven 3.8+
- Gemini API key
- Internet connection to access Gemini API endpoints

## Dependencies

The project relies on the following key dependencies:

- **Spring Boot 3.4.3**: Foundation framework
- **Spring AI OpenAI Starter 1.0.0-M6**: Spring's AI integration with OpenAI-compatible APIs
- **Spring Web**: For creating the REST controller

The complete dependency list is managed through Maven in the `pom.xml` file.

## Getting Started

### API Key Setup

Before running the application, you need to obtain a Gemini API key:

1. Visit the [Google AI Studio](https://ai.google.dev/) and create an account if needed
2. Navigate to the API keys section
3. Generate a new API key
4. Copy your API key for the next step

### Configuration

Update the `application.properties` file with your Gemini API key:

```properties
spring.ai.openai.api-key=YOUR_GEMINI_API_KEY
```

The other configurations in `application.properties` are already set up to point to Gemini's API endpoints:

```properties
spring.ai.openai.chat.base-url=https://generativelanguage.googleapis.com
spring.ai.openai.chat.completions-path=/v1beta/openai/chat/completions
spring.ai.openai.chat.options.model=gemini-2.0-flash
```

## Running the Application

### Using Maven

To run the application using Maven:

```bash
mvn spring-boot:run
```

### Using Java

Alternatively, you can build and run the application:

```bash
mvn clean package
java -jar target/hello-flash-0.0.1-SNAPSHOT.jar
```

Upon startup, the application will:

1. Execute the `CommandLineRunner` which queries Gemini for an interesting fact about Google
2. Expose a REST endpoint at `/models` to list available Gemini models

## Code Examples

### Command Line Interaction

The main application demonstrates how to create a simple prompt to Gemini:

```java
@Bean
CommandLineRunner commandLineRunner(ChatClient.Builder builder) {
    return args -> {
        var client = builder.build();
        var response = client.prompt("Tell me an interesting fact about Google")
                .call()
                .content();

        System.out.println(response);
    };
}
```

### REST Controller for Model Discovery

The application includes a controller to fetch available Gemini models. Unlike the command-line example, this doesn't use Spring AI but demonstrates a direct REST API approach using Spring's RestClient. This is particularly useful when you need to discover what models are available without prior knowledge:

```java
@RestController
public class GeminiModelController {

    @Value("${spring.ai.openai.api-key}")
    private String GEMINI_API_KEY;
    private final RestClient restClient;

    public GeminiModelController(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://generativelanguage.googleapis.com")
                .build();
    }

    @GetMapping("/models")
    public List<GeminiModel> models() {
        ResponseEntity<ModelListResponse> response = restClient.get()
                .uri("/v1beta/openai/models")
                .header("Authorization","Bearer " + GEMINI_API_KEY)
                .retrieve()
                .toEntity(ModelListResponse.class);
        return response.getBody().data();
    }
}
```

### Direct API Call Example

If you prefer to call the API directly without using Spring AI, you can use curl:

```bash
curl "https://generativelanguage.googleapis.com/v1beta/openai/chat/completions" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer YOUR_GEMINI_API_KEY" \
-d '{
    "model": "gemini-2.0-flash",
    "messages": [
        {"role": "user", "content": "Explain to me how AI works"}
    ]
}'
```

## How It Works

This project leverages Spring AI's OpenAI integration but reconfigures it to work with Gemini's API:

1. The base URL is changed to Gemini's endpoint (`generativelanguage.googleapis.com`)
2. The completions path is set to Gemini's OpenAI-compatible endpoint
3. The API key is set to your Gemini API key
4. The model is specified as `gemini-2.0-flash` (Gemini's fastest model)

The magic happens in the `application.properties` file, where these configurations redirect all Spring AI OpenAI client calls to Gemini's API.

## Advanced Usage

### Working with Different Gemini Models

Gemini offers several models with different capabilities. To use a different model:

```properties
spring.ai.openai.chat.options.model=gemini-2.0-pro
```

Available models include:
- `gemini-2.0-flash` (Fast responses)
- `gemini-2.0-pro` (Higher quality)
- `gemini-1.5-pro` (Previous generation)

Check the `/models` endpoint to see the complete list of available models.

### Custom Model Parameters

For advanced control over model behavior, you can modify your chat client:

```java
var options = ChatOptions.builder()
    .withTemperature(0.7f)  // Control randomness (0.0 to 1.0)
    .withMaxTokens(1024)    // Limit response length
    .build();

var client = builder
    .withOptions(options)
    .build();
```

## Troubleshooting

### Common Issues

1. **Authentication Error**: Make sure your Gemini API key is correct and properly set in `application.properties`

2. **Model Unavailable**: Verify that the model specified is available in your region. Use the `/models` endpoint to check available models.

3. **Rate Limiting**: Gemini API has rate limits. Check the response headers for rate limit information.

## Conclusion

The **Hello Flash** project demonstrates the flexibility of Spring AI and the OpenAI compatibility of Google's Gemini models. By redirecting API calls to Gemini's endpoints, you can easily switch between AI providers while maintaining a consistent development experience.

This approach allows you to:
- Leverage existing Spring AI and OpenAI knowledge
- Experiment with different AI providers
- Build applications that can work with multiple AI backends

Try extending this project with more complex prompts, different Gemini models, or additional Spring AI features.

## Resources

- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/index.html)
- [Google AI Studio](https://ai.google.dev/)
- [Gemini API Documentation](https://ai.google.dev/docs/gemini_api_overview)