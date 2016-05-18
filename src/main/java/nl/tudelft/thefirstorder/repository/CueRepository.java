package nl.tudelft.thefirstorder.repository;

import nl.tudelft.thefirstorder.domain.Cue;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cue entity.
 */
public interface CueRepository extends JpaRepository<Cue,Long> {

}
