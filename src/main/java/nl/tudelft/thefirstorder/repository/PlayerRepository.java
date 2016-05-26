package nl.tudelft.thefirstorder.repository;

import nl.tudelft.thefirstorder.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Player entity.
 */
public interface PlayerRepository extends JpaRepository<Player,Long> {

    List<Player> findByProjectId(Long projectId);

}
