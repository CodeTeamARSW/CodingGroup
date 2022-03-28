package edu.eci.arsw.controllers;



import edu.eci.arsw.service.LiveCodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.DatabaseStartupValidator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/livecoding")
public class LiveCodingAPIController {

	public String idRoom;

    @Autowired
    LiveCodingService lcs;

    @RequestMapping(value = "/users", method=RequestMethod.GET)
    public ResponseEntity getAllUsers(){
        System.out.println("Entrando en /users");
        return ResponseEntity.ok(lcs.getAllUsers());
    }

    @RequestMapping(value = "/saveRoomId", method=RequestMethod.POST)
    public ResponseEntity savRoomId(@RequestBody String idRoom){
        System.out.println("Entrando en /saveRoomId");
        this.idRoom = idRoom; 
        System.out.println("Dato guardado" + this.idRoom);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/getIdRoom", method=RequestMethod.GET)
    public ResponseEntity getIdRoom(){
        System.out.println("Entrando en /getIdRoom");
        return ResponseEntity.ok(idRoom);
    }

}
