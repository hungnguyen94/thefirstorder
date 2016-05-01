package nl.tudelft.thefirstorder.service;

import nl.tudelft.thefirstorder.domain.Camera;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Camera.
 */
public interface CameraService {

    /**
     * Save a camera.
     * 
     * @param camera the entity to save
     * @return the persisted entity
     */
    Camera save(Camera camera);

    /**
     *  Get all the cameras.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Camera> findAll(Pageable pageable);

    /**
     *  Get the "id" camera.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Camera findOne(Long id);

    /**
     *  Delete the "id" camera.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
