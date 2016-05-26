package nl.tudelft.thefirstorder.service;

import nl.tudelft.thefirstorder.domain.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Map.
 */
public interface MapService {

    /**
     * Save a map.
     * 
     * @param map the entity to save
     * @return the persisted entity
     */
    Map save(Map map);

    /**
     *  Get all the maps.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Map> findAll(Pageable pageable);

    /**
     *  Get the "id" map.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Map findOne(Long id);

    /**
     *  Delete the "id" map.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Adds a camera to the Map.
     * @param mapId Id of the Map
     * @param cameraId Id of the Camera
     * @return The updated map
     */
    Optional<Map> addCamera(Long mapId, Long cameraId);

    /**
     * Adds a player to the Map.
     * @param mapId Id of the Map
     * @param playerId Id of the Player
     * @return The updated map
     */
    Optional<Map> addPlayer(Long mapId, Long playerId);
}
