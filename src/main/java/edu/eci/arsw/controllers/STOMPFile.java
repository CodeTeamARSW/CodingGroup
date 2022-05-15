package edu.eci.arsw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class STOMPFile {

    @Autowired
    SimpMessagingTemplate smt;

    @MessageMapping("/file.{idroom}")
    public void handleMessageEvent(String message,  @DestinationVariable String idroom){
        smt.convertAndSend("/topic/file."+idroom, message);
    }

}