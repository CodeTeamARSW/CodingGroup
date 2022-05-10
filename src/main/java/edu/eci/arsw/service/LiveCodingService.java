package edu.eci.arsw.service;

import edu.eci.arsw.model.Code_Line;
import edu.eci.arsw.model.Message;
import edu.eci.arsw.model.Room;
import edu.eci.arsw.model.User;
import edu.eci.arsw.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiveCodingService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final RoomRepository roomRepository;

    private final Code_LineRepository code_lineRepository;

    private final FileRepository fileRepository;



    public LiveCodingService(UserRepository userRepository, ChatRepository chatRepository, MessageRepository messageRepository, RoomRepository roomRepository, Code_LineRepository code_lineRepository, FileRepository fileRepository){
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
        this.code_lineRepository = code_lineRepository;
        this.fileRepository = fileRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Room initRoom(String idRoom, String admin){
        return new Room(idRoom, admin);
    }
}