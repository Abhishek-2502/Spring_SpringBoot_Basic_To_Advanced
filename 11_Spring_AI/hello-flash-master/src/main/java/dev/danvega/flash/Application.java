// FOR ONLY CHAT FUNCTIONALITY
package dev.danvega.flash;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(ChatClient .Builder builder) {
		return args -> {
		    var client = builder.build();
			var response = client.prompt("Tell me an interesting fact about Google")
					.call()
					.content();

			System.out.println(response);
		};
	}
}

// FOR CHAT + EMBEDDING FUNCTIONALITY (Not Working yet)
//package dev.danvega.flash;
//
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.ai.embedding.EmbeddingModel;
//import org.springframework.ai.embedding.EmbeddingResponse;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//
//import java.util.Arrays;
//import java.util.List;
//
//@SpringBootApplication
//public class Application {
//
//    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
//    }
//
//    @Bean
//    CommandLineRunner commandLineRunner(ChatClient.Builder chatBuilder, EmbeddingModel embeddingModel) {
//        return args -> {
//            var chatClient = chatBuilder.build();
//
//            // Ask Gemini chat model
//            var response = chatClient.prompt("Tell me an interesting fact about Google")
//                    .call()
//                    .content();
//
//            System.out.println("\n💬 Chat Response:");
//            System.out.println(response);
//
//            try {
//                // Embed using Gemini embedding model
//                EmbeddingResponse embeddingResponse = embeddingModel.embedForResponse(List.of(response));
//
//                var embedding = embeddingResponse.getResults().get(0).getOutput();
//
//                System.out.println("Embedding length: " + embedding.length);
//                System.out.println("First 10 values: " +
//                        Arrays.toString(Arrays.copyOfRange(embedding, 0, 10)));
//            } catch (Exception e) {
//                System.err.println("⚠️ Embedding generation failed: " + e.getMessage());
//            }
//        };
//    }
//}
