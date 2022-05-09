package edu.eci.arsw.controllers;

import edu.eci.arsw.model.Room;
import edu.eci.arsw.service.LiveCodingService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/livecoding")
public class LiveCodingAPIController {

    public HashMap<String, Room> rooms = new HashMap<>();

    @Autowired
    LiveCodingService lcs;

    @RequestMapping(value = "/users", method=RequestMethod.GET)
    public ResponseEntity getAllUsers(){
        System.out.println("\n Recibiendo petición GET a '/users'");
        System.out.println("Entrando en /users");
        return ResponseEntity.ok(lcs.getAllUsers());
    }

    @RequestMapping(value = "/saveRoom", method=RequestMethod.POST)
    public ResponseEntity savRoomId(@RequestBody String body) {
        System.out.println("\n Recibiendo petición POST a '/saveRoom'");
        JSONObject textFile = new JSONObject(body);
        System.out.println("Entrando en /saveRoom -> " + textFile +" "+ textFile.get("idSala") +" "+ textFile.get("admin"));
        Room room = lcs.initRoom((String) textFile.get("idSala"), (String) textFile.get("admin"));
        rooms.put((String) textFile.get("idSala"), room);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/loadFile/{idSala}", method = RequestMethod.GET)
    public ResponseEntity loadFileRoom(@PathVariable String idSala){
        System.out.println("\n Recibiendo petición GET a '/loadFile/{idSala}'");
        System.out.println("Loading file -----------------------------------");
        Room room = null;
        room = rooms.get(idSala);
        System.out.println("Room File before if : " + room.getLocalFile());
        if (room != null) {
            System.out.println("LocalFile in array :" + rooms.get(idSala).getLocalFile());
            System.out.println("JSONArray: " + room.getLocalFile().toString());
            ArrayList<String> respuesta = new ArrayList<>();
            //Agregar nombre del archivo y líneas
            respuesta.add(room.getNameFile());
            respuesta.addAll(room.getLocalFile());
            System.out.println("Como se esta enviando...." + respuesta);
            return ResponseEntity.ok(respuesta);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/saveFile/{idSala}", method = RequestMethod.PUT)
    public ResponseEntity saveFile(@PathVariable String idSala, @RequestBody String body) {
        System.out.println("\n Recibiendo petición PUT a '/saveFile/{idSala}'");
        System.out.println("body Request: " + body);
        JSONArray textFile = new JSONArray(body);
        ArrayList<String> localFile = new ArrayList<>();
        //Guardando las líneas
        for (int i = 1; i<textFile.length(); i++) {
            localFile.add(textFile.getString(i));
        }
        //Guardar el nombre del archivo
        rooms.get(idSala).setNameFile(textFile.getString(0));
        //Guardar líneas
        rooms.get(idSala).setLocalFile(localFile);
        System.out.println("Archivo guardado: " + rooms.get(idSala).getLocalFile());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
