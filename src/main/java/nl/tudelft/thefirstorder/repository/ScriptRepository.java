package nl.tudelft.thefirstorder.repository;

import nl.tudelft.thefirstorder.domain.Script;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Script entity.
 */
public interface ScriptRepository extends JpaRepository<Script,Long> {

    List<Script> findByProjectIsNull();

}
