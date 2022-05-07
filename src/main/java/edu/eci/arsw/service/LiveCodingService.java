package edu.eci.arsw.service;

import edu.eci.arsw.model.Message;
import edu.eci.arsw.model.Room;
import edu.eci.arsw.model.User;
import edu.eci.arsw.repository.ChatRepository;
import edu.eci.arsw.repository.MessageRepository;
import edu.eci.arsw.repository.RoomRepository;
import edu.eci.arsw.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiveCodingService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final RoomRepository roomRepository;

    public LiveCodingService(UserRepository userRepository, ChatRepository chatRepository, MessageRepository messageRepository, RoomRepository roomRepository){
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Room initRoom(String idRoom, String admin){
        return new Room(idRoom, admin);
    }
}