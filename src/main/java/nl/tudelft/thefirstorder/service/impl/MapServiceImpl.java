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
import java.util.Optional;

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
    public Map save(Map map) {
        log.debug("Request to save Map : {}", map);
        Map result = mapRepository.save(map);
        return result;
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

    /**
     * Adds a camera to the Map.
     *
     * @param mapId    Id of the Map
     * @param cameraId Id of the Camera
     * @return The updated map
     */
    public Optional<Map> addCamera(Long mapId, Long cameraId) {
        Map map = findOne(mapId);
        return Optional.ofNullable(cameraService.findOne(cameraId))
                .map(camera -> {
                    log.debug("Request to add camera {} to map {}", mapId, cameraId);
                    map.addCamera(camera);
                    mapRepository.save(map);
                    return map;
                });
    }

    /**
     * Adds a player to the Map.
     *
     * @param mapId    Id of the Map
     * @param playerId Id of the Player
     * @return The updated map
     */
    @Override
    public Optional<Map> addPlayer(Long mapId, Long playerId) {
        Map map = findOne(mapId);
        return Optional.ofNullable(playerService.findOne(playerId))
                .map(player -> {
                    log.debug("Request to add player {} to map {}", mapId, playerId);
                    map.addPlayer(player);
                    return mapRepository.save(map);
                });
    }

}
