package com.practice.websocket.controller;

import com.practice.websocket.entity.ChatMessage;
import com.practice.websocket.entity.Room;
import com.practice.websocket.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @GetMapping("/rooms")
    public ModelAndView rooms() {
        ModelAndView mv = new ModelAndView("/rooms");

        List<Room> rooms = chatService.getAllRooms();
        mv.addObject("list", rooms);

        return mv;
    }

    @PostMapping("/room")
    public String create(@RequestParam String name, RedirectAttributes redirectAttributes) {

        Room room = chatService.createRoom(name);
        redirectAttributes.addFlashAttribute("roomName", room);

        return "redirect:/rooms";
    }
    //채팅방 조회
    @GetMapping("/room")
    public ModelAndView getRoom(@RequestParam(value = "roomId") String roomId){
        ModelAndView mv = new ModelAndView("/room");
        Room room = chatService.findByRoomId(roomId);
        mv.addObject("room", room);
        return mv;
    }


    @MessageMapping("/chat")
    @SendTo("/room-chat/{roomId}")
    public void messageHandler(@Payload ChatMessage message) {
        log.info("messageHandler= {}", message.toString());
        messagingTemplate.convertAndSend("/sub/room-chat/" + message.getRoomId(), message);
    }

    @MessageMapping("/enter")
    @SendTo("/room-chat/{roomId}")
    public void enter(@Payload ChatMessage message) {
        log.info("enter= {}", message.toString());
        message.setContent(message.getSender() + "님이 채팅방에 참여하셨습니다.");
        messagingTemplate.convertAndSend("/sub/room-chat/" + message.getRoomId(), message);
    }
}
