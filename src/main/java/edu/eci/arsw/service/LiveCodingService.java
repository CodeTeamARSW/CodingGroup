package edu.eci.arsw.service;

import edu.eci.arsw.model.User;
import edu.eci.arsw.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiveCodingService {

    private final UserRepository userRepository;

    public LiveCodingService(UserRepository userRepository){
        System.out.println("Iniciando user repository");
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll(); }

}
