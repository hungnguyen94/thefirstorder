package nl.tudelft.thefirstorder.service;

import nl.tudelft.thefirstorder.domain.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Player.
 */
public interface PlayerService {

    /**
     * Save a player.
     *
     * @param player the entity to save
     * @return the persisted entity
     */
    Player save(Player player);

    /**
     *  Get all the players.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Player> findAll(Pageable pageable);

    /**
     * Get the players, associated with the map with the given id.
     *
     * @param mapId the id of the map.
     * @return the list of player entities
     */
    List<Player> findPlayersByMap(Long mapId);

    /**
     *  Get the "id" player.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Player findOne(Long id);

    /**
     *  Delete the "id" player.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
