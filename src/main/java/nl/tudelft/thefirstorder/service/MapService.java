package nl.tudelft.thefirstorder.service;

import nl.tudelft.thefirstorder.domain.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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
     * Get all maps where project is null.
     * @return List of maps.
     */
    List<Map> findAllWhereProjectIsNull();
}
