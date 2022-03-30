package edu.eci.arsw.service;

import edu.eci.arsw.model.Room;
import edu.eci.arsw.model.User;
import edu.eci.arsw.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiveCodingService {

    private final UserRepository userRepository;
    private Room room;

    public LiveCodingService(UserRepository userRepository){
        this.userRepository = userRepository;

    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void initRoom(String idRoom){
        room = new Room(idRoom);
    }
}