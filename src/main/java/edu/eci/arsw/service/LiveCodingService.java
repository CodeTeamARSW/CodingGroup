package edu.eci.arsw.service;

import edu.eci.arsw.model.Room;
import edu.eci.arsw.model.User;
import edu.eci.arsw.repository.ChatRepository;
import edu.eci.arsw.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiveCodingService {

    private final UserRepository userRepository;
    private Room room;
    private ChatRepository chatRepository;

    public LiveCodingService(UserRepository userRepository, ChatRepository chatRepository){
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void initRoom(String idRoom, String author){
        room = new Room(idRoom,author);
    }
}