package edu.eci.arsw.repository;

import edu.eci.arsw.model.Code_Line;
import edu.eci.arsw.model.Code_LineID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Code_LineRepository extends JpaRepository<Code_Line, Code_LineID> {
}
