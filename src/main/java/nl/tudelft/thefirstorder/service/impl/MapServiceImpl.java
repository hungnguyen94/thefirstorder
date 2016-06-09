package nl.tudelft.thefirstorder.service.impl;

import nl.tudelft.thefirstorder.service.MapService;
import nl.tudelft.thefirstorder.domain.Map;
import nl.tudelft.thefirstorder.repository.MapRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Map.
 */
@Service
@Transactional
public class MapServiceImpl implements MapService{

    private final Logger log = LoggerFactory.getLogger(MapServiceImpl.class);
    
    @Inject
    private MapRepository mapRepository;
    
    /**
     * Save a map.
     * 
     * @param map the entity to save
     * @return the persisted entity
     */
    public Map save(Map map) {
        log.debug("Request to save Map : {}", map);
        Map result = mapRepository.save(map);
        return result;
    }

    /**
     *  Get all the maps.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Map> findAll(Pageable pageable) {
        log.debug("Request to get all Maps");
        Page<Map> result = mapRepository.findAll(pageable); 
        return result;
    }


    /**
     *  get all the maps where Project is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Map> findAllWhereProjectIsNull() {
        log.debug("Request to get all maps where Project is null");
        return StreamSupport
            .stream(mapRepository.findAll().spliterator(), false)
            .filter(map -> map.getProject() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get one map by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Map findOne(Long id) {
        log.debug("Request to get Map : {}", id);
        Map map = mapRepository.findOne(id);
        return map;
    }

    /**
     *  Delete the  map by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Map : {}", id);
        mapRepository.delete(id);
    }
}
