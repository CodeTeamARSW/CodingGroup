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
    public void handleMessageEvent(Message message, @DestinationVariable String idchat){
        smt.convertAndSend("/topic/message."+idchat, message);
    }

}