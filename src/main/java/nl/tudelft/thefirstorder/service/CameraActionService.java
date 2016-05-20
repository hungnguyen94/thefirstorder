package nl.tudelft.thefirstorder.service;

import nl.tudelft.thefirstorder.domain.CameraAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing CameraAction.
 */
public interface CameraActionService {

    /**
     * Save a cameraAction.
     * 
     * @param cameraAction the entity to save
     * @return the persisted entity
     */
    CameraAction save(CameraAction cameraAction);

    /**
     *  Get all the cameraActions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CameraAction> findAll(Pageable pageable);
    /**
     *  Get all the cameraActions where Cue is null.
     *  
     *  @return the list of entities
     */
    List<CameraAction> findAllWhereCueIsNull();

    /**
     *  Get the "id" cameraAction.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    CameraAction findOne(Long id);

    /**
     *  Delete the "id" cameraAction.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
