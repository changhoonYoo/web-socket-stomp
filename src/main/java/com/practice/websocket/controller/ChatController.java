package com.practice.websocket.controller;

import com.practice.websocket.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;


    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @MessageMapping("/{roomId}")
    @SendTo("/room/{roomId}")
    public void messageHandler(@DestinationVariable String roomId, @Payload ChatMessage message) {
        log.info(message.toString());
        messagingTemplate.convertAndSend("/sub/room/" + roomId, message);
    }
}
