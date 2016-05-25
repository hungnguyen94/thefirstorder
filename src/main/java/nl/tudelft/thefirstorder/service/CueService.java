package nl.tudelft.thefirstorder.service;

import nl.tudelft.thefirstorder.domain.Camera;
import nl.tudelft.thefirstorder.domain.Cue;
import nl.tudelft.thefirstorder.domain.Player;
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
     * Get the cues, associated with the script with the given id.
     *
     * @param scriptId the id of the script.
     * @return the list of cue entities
     */
    List<Cue> findCuesByMap(Long scriptId);

    /**
     * Get the player, belonging to the cue with the given id.
     *
     * @param cueId the id of the cue.
     * @return the player entity
     */
    Player getPlayer(Long cueId);

    /**
     * Get the camera, belonging to the cue with the given id.
     *
     * @param cueId the id of the cue.
     * @return the camera entity
     */
    Camera getCamera(Long cueId);

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
