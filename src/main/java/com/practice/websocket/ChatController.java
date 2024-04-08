package com.practice.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessageSendingOperations operations;

    @MessageMapping("/{roomId}")
    @SendTo("/user/{roomId}")
    public void messageHandler(@DestinationVariable String roomId, @Payload ChatMessage message) {
        log.info(message.toString());
        message.setContent("내용바꿔버리기");
        operations.convertAndSend("/sub/user/" + roomId, message);
    }

    /*
    setApplicationDestinationPrefixes는 @MessageMapping이 붙은 메서드를 호출한다.

    메세지를 발행하게 되면, /pub/channel 로 라우팅되고,

    /sub/channel/channelId를 구독하는 클라이언트로 메세지 객체가 전달된다.
     */
}
