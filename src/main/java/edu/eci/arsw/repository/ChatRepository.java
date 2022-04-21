package edu.eci.arsw.repository;

import edu.eci.arsw.model.Chat;
import edu.eci.arsw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatRepository extends JpaRepository<Chat, String>{
}