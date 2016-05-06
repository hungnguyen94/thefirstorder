package nl.tudelft.thefirstorder.repository;

import nl.tudelft.thefirstorder.domain.Player;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Player entity.
 */
public interface PlayerRepository extends JpaRepository<Player,Long> {

}
