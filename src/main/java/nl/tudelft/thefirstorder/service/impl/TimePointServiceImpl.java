package nl.tudelft.thefirstorder.service.impl;

import nl.tudelft.thefirstorder.domain.TimePoint;
import nl.tudelft.thefirstorder.repository.TimePointRepository;
import nl.tudelft.thefirstorder.service.TimePointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing TimePoint.
 */
@Service
@Transactional
public class TimePointServiceImpl implements TimePointService {

    private final Logger log = LoggerFactory.getLogger(TimePointServiceImpl.class);
    
    @Inject
    private TimePointRepository timePointRepository;
    
    /**
     * Save a timePoint.
     * 
     * @param timePoint the entity to save
     * @return the persisted entity
     */
    public TimePoint save(TimePoint timePoint) {
        log.debug("Request to save TimePoint : {}", timePoint);
        TimePoint result = timePointRepository.save(timePoint);
        return result;
    }

    /**
     *  Get all the timePoints.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<TimePoint> findAll(Pageable pageable) {
        log.debug("Request to get all TimePoints");
        Page<TimePoint> result = timePointRepository.findAll(pageable); 
        return result;
    }


    /**
     *  get all the timePoints where Cue is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<TimePoint> findAllWhereCueIsNull() {
        log.debug("Request to get all timePoints where Cue is null");
        return StreamSupport
            .stream(timePointRepository.findAll().spliterator(), false)
            .filter(timePoint -> timePoint.getCue() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get one timePoint by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public TimePoint findOne(Long id) {
        log.debug("Request to get TimePoint : {}", id);
        TimePoint timePoint = timePointRepository.findOne(id);
        return timePoint;
    }

    /**
     *  Delete the  timePoint by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TimePoint : {}", id);
        timePointRepository.delete(id);
    }
}
