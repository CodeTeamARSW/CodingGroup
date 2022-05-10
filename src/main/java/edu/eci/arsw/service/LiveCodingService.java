package edu.eci.arsw.service;

import edu.eci.arsw.model.Room;
import edu.eci.arsw.model.User;
import edu.eci.arsw.repository.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Service
public class LiveCodingService {

    //Conexiones a los mapeos de la DB
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final RoomRepository roomRepository;

    private final CodeLineRepository codelineRepository;

    private final FileRepository fileRepository;

    public LiveCodingService(UserRepository userRepository, ChatRepository chatRepository, MessageRepository messageRepository, RoomRepository roomRepository, CodeLineRepository codelineRepository, FileRepository fileRepository){
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
        this.codelineRepository = codelineRepository;
        this.fileRepository = fileRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Room initRoom(String idRoom, String admin){
        Room room = new Room(idRoom, admin);
        roomRepository.save(room);
        return room;
    }
}