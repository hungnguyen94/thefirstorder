package nl.tudelft.thefirstorder.service;

import nl.tudelft.thefirstorder.domain.Map;
import nl.tudelft.thefirstorder.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Project.
 */
public interface ProjectService {

    /**
     * Save a project.
     *
     * @param project the entity to save
     * @return the persisted entity
     */
    Project save(Project project);

    /**
     *  Get all the projects.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Project> findAll(Pageable pageable);

    /**
     *  Get the "id" project.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Project findOne(Long id);

    /**
     *  Get the map, associated with the given project.
     *
     *  @param projectId the id of the Project entity
     *  @return the Map entity
     */
    Map getMap(Long projectId);

    /**
     *  Delete the "id" project.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
