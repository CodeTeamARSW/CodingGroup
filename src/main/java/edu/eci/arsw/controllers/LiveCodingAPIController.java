package edu.eci.arsw.controllers;


import edu.eci.arsw.model.User;
import edu.eci.arsw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/livecoding")
public class LiveCodingAPIController {

    private final UserRepository userRepository;

    public LiveCodingAPIController(UserRepository userRepository){
        System.out.println("Iniciando user repository");
        this.userRepository = userRepository;
    }


    //getUsers

    @GetMapping(value = "/users")
    public ResponseEntity getAllUsers(){
        System.out.println("Entrando en /users");
        return ResponseEntity.ok(this.userRepository.findAll());
    }


}
