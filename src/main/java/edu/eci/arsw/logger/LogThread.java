package edu.eci.arsw.logger;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;

@Service
/**
 * LiveCodingLog is a class that represents a log of live coding events.
 */
public class LiveCodingLog {

    public void saveIntoDB(String idRoom) throws Exception {
    }

    public void createEvent(String idRoom, String activity, String user, String type){
        Event evt = new Event(activity,user,type);
    }

}