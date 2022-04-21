package edu.eci.arsw.repository;


import edu.eci.arsw.model.Message;
import edu.eci.arsw.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, String>  {
}
