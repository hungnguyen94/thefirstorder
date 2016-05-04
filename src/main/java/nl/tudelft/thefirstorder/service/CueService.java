package nl.tudelft.thefirstorder.service;

import nl.tudelft.thefirstorder.domain.Cue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Cue.
 */
public interface CueService {

    /**
     * Save a cue.
     * 
     * @param cue the entity to save
     * @return the persisted entity
     */
    Cue save(Cue cue);

    /**
     *  Get all the cues.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Cue> findAll(Pageable pageable);

    /**
     *  Get the "id" cue.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Cue findOne(Long id);

    /**
     *  Delete the "id" cue.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
