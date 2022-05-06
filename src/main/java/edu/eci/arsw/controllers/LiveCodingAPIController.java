package edu.eci.arsw.controllers;

import edu.eci.arsw.model.Room;
import edu.eci.arsw.service.LiveCodingService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/livecoding")
public class LiveCodingAPIController {

	//public String idRoom;
    public HashMap<String, Room> rooms = new HashMap<>();

    @Autowired
    LiveCodingService lcs;

    @RequestMapping(value = "/users", method=RequestMethod.GET)
    public ResponseEntity getAllUsers(){
        System.out.println("Entrando en /users");
        return ResponseEntity.ok(lcs.getAllUsers());
    }

    /*@RequestMapping(value = "/saveRoomId", method=RequestMethod.POST)
    public ResponseEntity savRoomId(@RequestBody String idRoom){
        System.out.println("Entrando en /saveRoomId");
        this.idRoom = idRoom; 
        lcs.initRoom(idRoom, "1");
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    @RequestMapping(value = "/saveRoom", method=RequestMethod.POST)
    public ResponseEntity savRoomId(@RequestBody String idRoom) {
        System.out.println("Entrando en /saveRoom");
        Room room = lcs.initRoom(idRoom, "1");  /* ******************* */
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @RequestMapping(value = "/getIdRoom", method=RequestMethod.GET)
    public ResponseEntity getIdRoom(){
        System.out.println("Entrando en /getIdRoom");
        return ResponseEntity.ok(idRoom);
    }*/

    @RequestMapping(value = "/loadFile/{idSala}", method = RequestMethod.GET)
    public ResponseEntity loadFileRoom(@PathVariable String idSala){
        Room room = null;
        room = rooms.get(idSala);
        if (room != null) {
            return ResponseEntity.ok(room.getFile());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/saveFile/{idSala}", method = RequestMethod.PUT)
    public ResponseEntity saveFile(@PathVariable String idSala, @RequestBody String body) {
        JSONObject textFile = new JSONObject(body);
        // Guardar el archivo en un HashMap y enviarlo a la base de datos
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
