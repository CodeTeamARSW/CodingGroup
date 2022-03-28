package edu.eci.arsw.controllers;



import edu.eci.arsw.service.LiveCodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/livecoding")
public class LiveCodingAPIController {


    @Autowired
    LiveCodingService lcs;

    @RequestMapping(value = "/users", method=RequestMethod.GET)
    public ResponseEntity getAllUsers(){
        System.out.println("Entrando en /users");
        return ResponseEntity.ok(lcs.getAllUsers());
    }



}
