package edu.eci.arsw.logger;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Service
/**
 * LogThread is a class that represents a log of live coding events.
 */
public class LogThread extends Thread{

    private Event event;

    public LogThread(){

    }

    public LogThread(String idRoom, String activity, String user, String type){
        this.event = new Event(idRoom, activity, user, type);
    }

    @Override
    public void run(){
        System.out.println("<================= Se entro al run del hilo ==================>");
        System.out.println(event.toString());
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Persistence" );
        EntityManager entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();
        entitymanager.persist(event);
        entitymanager.getTransaction().commit();
        System.out.println("<================= Finalizo el run del hilo ==================>");
    }

}