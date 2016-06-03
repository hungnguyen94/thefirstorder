package nl.tudelft.thefirstorder.service.impl;

import nl.tudelft.thefirstorder.domain.Map;
import nl.tudelft.thefirstorder.repository.MapRepository;
import nl.tudelft.thefirstorder.service.CameraService;
import nl.tudelft.thefirstorder.service.MapService;
import nl.tudelft.thefirstorder.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Map.
 */
@Service
@Transactional
public class MapServiceImpl implements MapService {

    private final Logger log = LoggerFactory.getLogger(MapServiceImpl.class);

    @Inject
    private MapRepository mapRepository;

    @Inject
    private CameraService cameraService;

    @Inject
    private PlayerService playerService;

    /**
     * Save a map.
     *
     * @param map the entity to save
     * @return the persisted entity
     */
    @Transactional
    public Map save(Map map) {
        log.debug("Request to save Map : {}", map);
        return mapRepository.save(map);
    }

    /**
     * Get all the maps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Map> findAll(Pageable pageable) {
        log.debug("Request to get all Maps");
        Page<Map> result = mapRepository.findAll(pageable);
        return result;
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
        return mapRepository.findOne(id);
    }

    /**
     *  Delete the  map by id.
     *  
     *  @param id the id of the entity
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Map : {}", id);
        mapRepository.delete(id);
    }

    /**
     * Get all maps where project is null.
     *
     * @return List of maps.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map> findAllWhereProjectIsNull() {
        return mapRepository.findByProjectIsNull();
    }

}
