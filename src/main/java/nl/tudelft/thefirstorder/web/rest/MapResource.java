package nl.tudelft.thefirstorder.web.rest;

import com.codahale.metrics.annotation.Timed;
import nl.tudelft.thefirstorder.domain.Map;
import nl.tudelft.thefirstorder.service.MapService;
import nl.tudelft.thefirstorder.web.rest.dto.MapDTO;
import nl.tudelft.thefirstorder.web.rest.util.HeaderUtil;
import nl.tudelft.thefirstorder.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Map.
 */
@RestController
@RequestMapping("/api")
public class MapResource {

    private final Logger log = LoggerFactory.getLogger(MapResource.class);
        
    @Inject
    private MapService mapService;

    /**
     * POST  /maps : Create a new map.
     *
     * @param map the map to create
     * @return the ResponseEntity with status 201 (Created) and with body the new map,
     *      or with status 400 (Bad Request) if the map has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/maps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> createMap(@RequestBody Map map) throws URISyntaxException {
        log.debug("REST request to save Map : {}", map);
        if (map.getId() != null) {
            return ResponseEntity.badRequest().headers(
                    HeaderUtil.createFailureAlert("map", "idexists", "A new map cannot already have an ID")
            ).body(null);
        }
        Map result = mapService.save(map);
        return ResponseEntity.created(new URI("/api/maps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("map", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /maps : Updates an existing map.
     *
     * @param map the map to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated map,
     *      or with status 400 (Bad Request) if the map is not valid,
     *      or with status 500 (Internal Server Error) if the map couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/maps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> updateMap(@RequestBody Map map) throws URISyntaxException {
        log.debug("REST request to update Map : {}", map);
        if (map.getId() == null) {
            return createMap(map);
        }
        Map result = mapService.save(map);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("map", map.getId().toString()))
            .body(result);
    }

    /**
     * GET  /maps : get all the maps.
     *
     * @param pageable the pagination information
     * @param filter the filter of this request
     * @return the ResponseEntity with status 200 (OK) and the list of maps in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/maps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map>> getAllMaps(Pageable pageable,
                                                @RequestParam(required = false) String filter)
            throws URISyntaxException {
        if ("project-is-null".equals(filter)) {
            log.debug("REST request to get all Maps where project is null");
            return new ResponseEntity<>(mapService.findAllWhereProjectIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Maps");
        Page<Map> page = mapService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/maps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /maps/:id : get the "id" map.
     *
     * @param id the id of the map to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the map, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/maps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getMap(@PathVariable Long id) {
        log.debug("REST request to get Map : {}", id);
        Map map = mapService.findOne(id);
        return Optional.ofNullable(map)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /maps/:id : delete the "id" map.
     *
     * @param id the id of the map to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/maps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMap(@PathVariable Long id) {
        log.debug("REST request to delete Map : {}", id);
        mapService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("map", id.toString())).build();
    }

//    /**
//     * PUT  /maps/{mapId}/addCamera?cameraId={cameraId}
//     * Adds a (existing) camera to the map.
//     * @param mapId Map id
//     * @param cameraId Camera id
//     * @return ResponseEntity with status OK if succeeded, or status 404 if an error occurred.
//     */
//    @RequestMapping(value = "/maps/{mapId}/addCamera",
//        method = RequestMethod.PUT,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    @Transactional
//    public ResponseEntity<Map> addCameraToMap(@PathVariable Long mapId, @RequestParam Long cameraId) {
//        return mapService.addCamera(mapId, cameraId)
//                .map(map -> new ResponseEntity<>(map, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    /**
//     * PUT  /maps/{mapId}/addPlayer?playerId={playerId}
//     * Adds a (existing) player to the map.
//     * @param mapId Map id
//     * @param playerId Player id
//     * @return ResponseEntity with status OK if succeeded, or status 404 if an error occurred.
//     */
//    @RequestMapping(value = "/maps/{mapId}/addPlayer",
//            method = RequestMethod.PUT,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    @Transactional
//    public ResponseEntity<Map> addPlayerToMap(@PathVariable Long mapId, @RequestParam Long playerId) {
//        return mapService.addPlayer(mapId, playerId)
//                .map(map -> new ResponseEntity<>(map, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    @RequestMapping(value = "/maps/{mapId}/dto",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<MapDTO> getMapDTO(@PathVariable Long mapId) {
        return Optional.ofNullable(mapService.findOne(mapId))
                .map(map -> new ResponseEntity<MapDTO>(new MapDTO(map), HttpStatus.OK))
                .orElse(new ResponseEntity<MapDTO>(HttpStatus.NOT_FOUND));
    }

}
