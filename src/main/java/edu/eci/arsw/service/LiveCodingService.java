package edu.eci.arsw.service;

import edu.eci.arsw.model.Chat;
import edu.eci.arsw.model.Room;
import edu.eci.arsw.model.User;
import edu.eci.arsw.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class LiveCodingService {

    //Conexiones a los mapeos de la DB
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final RoomRepository roomRepository;

    private final CodeLineRepository codelineRepository;

    private final FileRepository fileRepository;

    private HashMap<String, ArrayList<String>> files = new HashMap<>();

    public LiveCodingService(UserRepository userRepository, ChatRepository chatRepository, MessageRepository messageRepository, RoomRepository roomRepository, CodeLineRepository codelineRepository, FileRepository fileRepository){
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
        this.codelineRepository = codelineRepository;
        this.fileRepository = fileRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }



    /**
     * This function creates a new room with the given idRoom, admin, and intialLine
     *
     * @param idRoom The id of the room
     * @param admin the user who created the room
     * @param intialLine The first line of the file.
     * @return A room object
     */
    public Room initRoom(String idRoom, String admin, String intialLine){
        Room room = new Room(idRoom, admin);
        Chat chat = new Chat(idRoom, idRoom);
        ArrayList<String> localFile = new ArrayList<>();
        localFile.add(intialLine);
        files.put(idRoom, localFile);
        roomRepository.save(room);
        chatRepository.save(chat);
        return room;
    }

    /**
     * It returns the list of files in a room.
     *
     * @param idRoom the id of the room
     * @return An ArrayList of Strings
     */
    public ArrayList<String> getLocalFileByIDRoom(String idRoom){
        return files.get(idRoom);
    }

    /**
     * It replaces the value of the key "idRoom" with the value "localFile" in the HashMap "files"
     *
     * @param idRoom The ID of the room you want to set the local file list for.
     * @param localFile The list of files that are in the local directory.
     */
    public void setLocalFileByIDRoom(String idRoom, ArrayList<String> localFile) {
        files.replace(idRoom, localFile);

        System.out.println(files.get(idRoom));
    }
}