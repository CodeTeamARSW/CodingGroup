package edu.eci.arsw.controllers;


import edu.eci.arsw.model.User;
import edu.eci.arsw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 *
 */
@RestController
@RequestMapping(value = "/livecoding")
public class LiveCodingAPIController {
    
    private final UserRepository userRepository;

    public LiveCodingAPIController(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    //getUsers

   /* @RequestMapping(value = "/users", method = RequestMethod.GET)
    private List<User> getAllUsers(){
        return this.userRepository.findAll();
    }*/
}
