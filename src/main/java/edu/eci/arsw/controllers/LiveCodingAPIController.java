package edu.eci.arsw.controllers;

import edu.eci.arsw.model.Message;
import edu.eci.arsw.model.Room;
import edu.eci.arsw.service.LiveCodingService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    /**
     * It receives a POST request with a JSON object containing the room's ID, the admin's ID and the initial line of code.
     * It then creates a new Room object and adds it to the HashMap
     *
     * @param body The body of the request, which is a JSON object.
     * @return A ResponseEntity with the status OK.
     */
    @RequestMapping(value = "/saveRoom", method=RequestMethod.POST)
    public ResponseEntity savRoomId(@RequestBody String body) {
        System.out.println("\n Recibiendo petición POST a '/saveRoom'");
        JSONObject textFile = new JSONObject(body);
        System.out.println("Entrando en /saveRoom -> " + textFile +" "+ textFile.get("idSala") +" "+ textFile.get("admin"));
        Room room = lcs.initRoom((String) textFile.get("idSala"), (String) textFile.get("admin"), (String) textFile.get("intialLine"));
        rooms.put((String) textFile.get("idSala"), room);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * It loads the file of a room.
     *
     * @param idSala The id of the room that the user is in.
     * @return The file of the room is being returned.
     */
    @RequestMapping(value = "/loadFile/{idSala}", method = RequestMethod.GET)
    public ResponseEntity loadFileRoom(@PathVariable String idSala){
        System.out.println("\n Recibiendo petición GET a '/loadFile/{idSala}'");
        System.out.println("Loading file -----------------------------------");
        ArrayList<String> fileRoom = lcs.getLocalFileByIDRoom(idSala);
        if (fileRoom != null) {
            System.out.println("LocalFile in array :" + fileRoom);
            return ResponseEntity.ok(fileRoom);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value= "/loadChat/{idSala}", method = RequestMethod.GET)
    public ResponseEntity loadChat(@PathVariable String idSala){
        List<Object[]> messages = lcs.searchMessages(idSala);
        return ResponseEntity.ok(messages);
    }

    @RequestMapping(value = "/saveFile/{idSala}", method = RequestMethod.GET)
    public ResponseEntity saveFile(@PathVariable String idSala) {
        System.out.println("\n Recibiendo petición POST a '/saveFile/{idSala}'");
        /**JSONArray textFile = new JSONArray(body);
        ArrayList<String> localFile = new ArrayList<>();
        //Guardando las líneas
        for (int i = 1; i<textFile.length(); i++) {
            localFile.add(textFile.getString(i));
        }**/
        lcs.persistentFile(idSala);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * It receives a JSONArray, converts it to an ArrayList, and then updates the local file with the new ArrayList
     *
     * @param idSala The id of the room where the file is located.
     * @param body The body of the request, which is a JSONArray with the lines of the file.
     * @return The response is a JSONArray with the updated file.
     */
    @RequestMapping(value = "/autoSave/{idSala}", method = RequestMethod.PUT)
    public ResponseEntity autoSave(@PathVariable String idSala, @RequestBody String body) {
        System.out.println("\n Recibiendo petición PUT a '/autoSave/{idsala}'");
        System.out.println("<- Antes del JSONArray ->");
        System.out.println(body);
        JSONArray file = new JSONArray(body);
        System.out.println("<- Despues del JSONArray en la posi 0 ->");
        System.out.println(file.getString(0));
        ArrayList<String> update = new ArrayList<>();
        for (int i  = 0; i<file.length(); i++){
            update.add(file.getString(i));
        }
        lcs.setLocalFileByIDRoom(idSala, update);
        System.out.println("<-----------ARCHIVO DESPUES DEL UPDATE------------->");
        System.out.println(update);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/autoSaveMessage/{idSala}", method = RequestMethod.PUT)
    public ResponseEntity autoSaveMessage(@PathVariable String idSala, @RequestBody String body) {
        System.out.println("Recibiendo petición PUT para message");
        System.out.println(body);
        JSONObject message = new JSONObject(body);
        lcs.setMessageByIDRoom(idSala, message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}