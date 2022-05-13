package edu.eci.arsw.logger;

import java.time.LocalDateTime;
import java.util.Date;

public class Event {
    //Fecha, Actividad, Usuario, Tipo
    LocalDateTime date;
    String activity;
    String user;
    String type;



    // A constructor of the class Event.
    public Event( String activity, String user, String type){
        this.date = LocalDateTime.now();
        this.activity = activity;
        this.user = user;
        this.type = type;
    }


}
