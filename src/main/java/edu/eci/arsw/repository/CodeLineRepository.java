package edu.eci.arsw.repository;

import edu.eci.arsw.model.CodeLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeLineRepository extends JpaRepository<CodeLine, String> {
}
