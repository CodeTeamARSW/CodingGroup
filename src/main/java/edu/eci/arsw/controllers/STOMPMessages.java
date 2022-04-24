package edu.eci.arsw.controllers;

import edu.eci.arsw.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class STOMPMessages {

    @Autowired
    SimpMessagingTemplate smt;

    @MessageMapping("/message.{idchat}")
    public void handleMessageEvent(String message, @DestinationVariable String idchat){
        System.out.println("------------Entro en STOMP---Message------- ");
        smt.convertAndSend("/topic/message."+idchat, message);
    }

}