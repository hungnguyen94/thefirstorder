package nl.tudelft.thefirstorder.service.impl;

import nl.tudelft.thefirstorder.domain.Map;
import nl.tudelft.thefirstorder.repository.MapRepository;
import nl.tudelft.thefirstorder.service.MapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Map.
 */
@Service
@Transactional
public class MapServiceImpl implements MapService {

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
     *  Get one map by projectId.
     *
     *  @param id the projectId of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Map findOne(Long id) {
        log.debug("Request to get Map : {}", id);
        Map map = mapRepository.findOne(id);
        return map;
    }

    /**
     *  Get all maps belonging to project projectId.
     *
     *  @param projectId the projectId of the project entity
     *  @return the map entity
     */
    @Transactional(readOnly = true)
    public List<Map> findMapsByProject(Long projectId) {
        log.debug("Request to get Maps for Project: {}", projectId);
        return StreamSupport
            .stream(mapRepository.findAll().spliterator(), false)
            .filter(map -> Objects.equals(map.getId(), projectId))
            .collect(Collectors.toList());
    }

    /**
     *  Delete the  map by projectId.
     *
     *  @param id the projectId of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Map : {}", id);
        mapRepository.delete(id);
    }
}
