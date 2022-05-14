package edu.eci.arsw.repository;

import edu.eci.arsw.logger.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {
}
