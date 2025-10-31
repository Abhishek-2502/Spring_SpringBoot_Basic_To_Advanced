//package com.example.models.controller;
//
//import org.springframework.ai.chat.messages.UserMessage;
//import org.springframework.ai.chat.model.ChatResponse;
//import org.springframework.ai.chat.prompt.Prompt;
//import org.springframework.ai.google.genai.GoogleGenAiChatModel;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/gemini")
//@CrossOrigin("*")
//public class GeminiController {
//    private final GoogleGenAiChatModel chatModel;
//
//    @Autowired
//    public GeminiController(GoogleGenAiChatModel chatModel){
//        this.chatModel = chatModel;
//    }
//
//    @GetMapping("/{message}")
//    public ResponseEntity<String> getAnswer(@PathVariable String message){
//
//        // Create a user prompt
//        Prompt prompt = new Prompt(new UserMessage(message));
//
//        // Call Gemini model
//        ChatResponse chatResponse = chatModel.call(prompt);
//
//        // Extract text response
//        String response = chatResponse.getResult().getOutput().getText();
//
//        return ResponseEntity.ok(response);
//    }
//}
//
//
