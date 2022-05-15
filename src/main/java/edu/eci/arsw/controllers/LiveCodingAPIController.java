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
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/livecoding")
public class LiveCodingAPIController {

    public HashMap<String, Room> rooms = new HashMap<>();

    @Autowired
    LiveCodingService lcs;

    /**
     * It receives a POST request with a JSON object containing the room's ID, the admin's ID and the initial line of code.
     * It then creates a new Room object and adds it to the HashMap
     *
     * @param body The body of the request, which is a JSON object.
     * @return A ResponseEntity with the status OK.
     */
    @RequestMapping(value = "/saveRoom", method=RequestMethod.POST)
    public ResponseEntity savRoomId(@RequestBody String body) {
        JSONObject textFile = new JSONObject(body);
        Room room = lcs.initRoom((String) textFile.get("idSala"), (String) textFile.get("admin"), (String) textFile.get("intialLine"));
        rooms.put((String) textFile.get("idSala"), room);
        lcs.saveEventLog(textFile.getString("idSala"), "Create new room for cooperative coding", textFile.getString("admin"), "INFO");
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
        ArrayList<String> fileRoom = lcs.getLocalFileByIDRoom(idSala);
        if (fileRoom != null) {
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
        JSONArray file = new JSONArray(body);
        ArrayList<String> update = new ArrayList<>();
        for (int i  = 0; i<file.length(); i++){
            update.add(file.getString(i));
        }
        lcs.setLocalFileByIDRoom(idSala, update);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(value = "/autoSaveMessage/{idSala}", method = RequestMethod.PUT)
    public ResponseEntity autoSaveMessage(@PathVariable String idSala, @RequestBody String body) {
        JSONObject message = new JSONObject(body);
        lcs.setMessageByIDRoom(idSala, message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/registryLogs/{idSala}", method = RequestMethod.POST)
    public ResponseEntity registerLog(@PathVariable String idSala, @RequestBody String body){
        JSONObject log = new JSONObject(body);
        lcs.saveEventLog(idSala, log.getString("activity"), log.getString("user"), log.getString("type"));
        return new ResponseEntity(HttpStatus.OK);
    }
}