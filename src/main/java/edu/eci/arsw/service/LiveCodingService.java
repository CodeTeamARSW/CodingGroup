package edu.eci.arsw.service;

import edu.eci.arsw.logger.Event;
import edu.eci.arsw.logger.LogThread;
import edu.eci.arsw.model.*;
import edu.eci.arsw.repository.*;
import org.apache.tomcat.util.digester.ArrayStack;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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

    private final EventRepository eventRepository;

    private HashMap<String, ArrayList<String>> files = new HashMap<>();

    public LiveCodingService(UserRepository userRepository, ChatRepository chatRepository, MessageRepository messageRepository, RoomRepository roomRepository, CodeLineRepository codelineRepository, FileRepository fileRepository, EventRepository eventRepository){
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.roomRepository = roomRepository;
        this.codelineRepository = codelineRepository;
        this.fileRepository = fileRepository;
        this.eventRepository = eventRepository;
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
    }

    /**
     * It takes the lines of a file and saves them in the database.
     *
     * @param idRoom the id of the room.
     */
    public void persistentFile(String idRoom){
        ArrayList<String> lines = new ArrayStack<>();
        lines = getLocalFileByIDRoom(idRoom);
        File file = new File(idRoom,lines.get(0), idRoom);
        fileRepository.save(file);
        for (int i=1; i< lines.size(); i++){
            CodeLine cl = new CodeLine(idRoom.concat(java.lang.String.valueOf(i)), i, idRoom, lines.get(i));
            codelineRepository.save(cl);
        }
    }

    /**
     * It receives a JSONArray with the message, the user and the room, and saves it in the database
     *
     * @param idSala The id of the room where the message will be saved.
     * @param message JSONArray with the following structure:
     */
    public void setMessageByIDRoom(String idSala, JSONObject message) {
        Message newMessage = new Message((String) message.get("user"), (String) message.get("idRoom"), (String) message.get("content"));
        messageRepository.save(newMessage);
    }

    /**
     * It returns a list of objects, each object is an array of two strings, the first string is the author of the message
     * and the second string is the content of the message
     *
     * @param idSala the id of the chat room
     * @return A list of objects.
     */
    public List<Object[]> searchMessages(String idSala) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Persistence" );
        EntityManager entitymanager = emfactory.createEntityManager();
        Query query = entitymanager.createNativeQuery( "Select m.author, m.content  from Messages m where m.idchat ='" + idSala + "'");
        List<Object[]> list1=(List<Object[]>)query.getResultList( );
        return list1;
    }

    /**
     * It creates a new thread that will save the event log in the database
     *
     * @param idRoom The id of the room where the event happened.
     * @param activity the activity that the user is doing
     * @param user The user who performed the action
     * @param type
     */
    public void saveEventLog(String idRoom, String activity, String user, String type){
        LogThread lt = new LogThread(idRoom, activity, user, type);
        lt.start();
    }
}