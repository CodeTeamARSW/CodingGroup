package edu.eci.arsw.logger;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @Column(name = "idevent")
    private Integer idevent;

    @Column(name = "creation_date")
    LocalDateTime date;

    @Column(name = "idroom")
    String idroom;

    @Column(name = "activity")
    String activity;

    @Column(name = "author")
    String user;

    @Column(name = "type_evt")
    String type;

    public Event() {
    }

    // A constructor of the class Event.
    public Event(String idroom, String activity, String user, String type){
        Random random = new Random();
        this.idevent = random.nextInt(999999999-1)+1;
        this.idroom = idroom;
        this.date = LocalDateTime.now();
        this.activity = activity;
        this.user = user;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Event{" +
                "idevent=" + idevent +
                ", date=" + date +
                ", idroom='" + idroom + '\'' +
                ", activity='" + activity + '\'' +
                ", user='" + user + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}