package com.practice.websocket.service;

import com.practice.websocket.entity.Room;
import com.practice.websocket.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChatService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room createRoom(String name) {
        Room room = Room.builder().name(name).build();
        roomRepository.save(room);

        return room;
    }

    public Room findByRoomId(String roomId) {
        return roomRepository.findById(roomId).orElse(null);
    }
}
