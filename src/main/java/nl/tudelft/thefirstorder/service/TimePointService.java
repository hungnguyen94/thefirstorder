package nl.tudelft.thefirstorder.service;

import nl.tudelft.thefirstorder.domain.TimePoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing TimePoint.
 */
public interface TimePointService {

    /**
     * Save a timePoint.
     * 
     * @param timePoint the entity to save
     * @return the persisted entity
     */
    TimePoint save(TimePoint timePoint);

    /**
     *  Get all the timePoints.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TimePoint> findAll(Pageable pageable);

    /**
     *  Get all the timePoints where Cue is null.
     *  
     *  @return the list of entities
     */
    List<TimePoint> findAllWhereCueIsNull();

    /**
     *  Get the "id" timePoint.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    TimePoint findOne(Long id);

    /**
     *  Delete the "id" timePoint.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
