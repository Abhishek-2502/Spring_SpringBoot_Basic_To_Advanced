package com.example.models.controller;


import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/anthropic")
@CrossOrigin("*")
public class AnthropicController {

    private ChatClient chatClient;

    public AnthropicController(AnthropicChatModel chatModel){
        this.chatClient = ChatClient.create(chatModel);
    }

    @GetMapping("/{message}")
    public ResponseEntity<String> getAnswer(@PathVariable String message){

        String response = chatClient
                .prompt(message)
                .call()
                .content();

        return ResponseEntity.ok(response);
    }

}
