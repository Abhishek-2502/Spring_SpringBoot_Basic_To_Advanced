package com.chat.app.chatroomapp.controllers;

import com.chat.app.chatroomapp.models.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @MessageMapping("/chat") // Client will send messages to /app/chat
    @SendTo("/topic/messages") // Messages will be broadcast to /topic/messages (subscribed by clients)
    public Message getContent(@RequestBody Message message) {

//         Simulate processing delay (optional, for demonstration purposes)
//         This can be removed in a real application
//        try{
//            Thread.sleep(2000);
//        }catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return message;
    }


}
