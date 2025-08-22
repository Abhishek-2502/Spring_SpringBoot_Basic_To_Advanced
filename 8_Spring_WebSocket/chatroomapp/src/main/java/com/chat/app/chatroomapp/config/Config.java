package com.chat.app.chatroomapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class Config implements WebSocketMessageBrokerConfigurer {

    // Register the WebSocket endpoint that clients will use to connect to the server.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /* The endpoint is "/ws" and it supports SockJS fallback options.
        To connect to this WebSocket server, clients will use the endpoint "/ws" with SockJS support.
        */
        registry.addEndpoint("/ws").withSockJS();

    }

    // Configure the message broker to enable a simple in-memory message broker.
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /*
        This broker will handle messages sent to destinations prefixed with "/topic".
        The server will broadcast messages to destinations prefixed with "/topic" (e.g., "/topic/messages").
        */
        registry.enableSimpleBroker("/topic");

        /*
        It sets the application destination prefix to "/app", which is used for sending messages from clients to the server.
        Clients will send messages to destinations prefixed with "/app" (e.g., "/app/chat").
         */
        registry.setApplicationDestinationPrefixes("/app");
    }
}
