package com.practice.websocket.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.Objects;

@Slf4j
@Configuration
public class WebSocketInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        StompCommand command = headerAccessor.getCommand();

        switch (Objects.requireNonNull(command)) {
            case CONNECT, DISCONNECT -> {
                log.info("Now {}", command);
            }
            case SUBSCRIBE, UNSUBSCRIBE -> {
                log.info("{} Successful", command);
                log.info("Destination is {}", headerAccessor.getDestination());
            }
            default -> log.error("Connection Error. Command is {}", command);
        }
        return message;
    }
}
