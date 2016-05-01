package nl.tudelft.thefirstorder.web.rest;

import com.codahale.metrics.annotation.Timed;
import nl.tudelft.thefirstorder.domain.CameraAction;
import nl.tudelft.thefirstorder.service.CameraActionService;
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
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CameraAction.
 */
@RestController
@RequestMapping("/api")
public class CameraActionResource {

    private final Logger log = LoggerFactory.getLogger(CameraActionResource.class);
        
    @Inject
    private CameraActionService cameraActionService;
    
    /**
     * POST  /camera-actions : Create a new cameraAction.
     *
     * @param cameraAction the cameraAction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cameraAction, or with status 400 (Bad Request) if the cameraAction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/camera-actions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CameraAction> createCameraAction(@RequestBody CameraAction cameraAction) throws URISyntaxException {
        log.debug("REST request to save CameraAction : {}", cameraAction);
        if (cameraAction.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cameraAction", "idexists", "A new cameraAction cannot already have an ID")).body(null);
        }
        CameraAction result = cameraActionService.save(cameraAction);
        return ResponseEntity.created(new URI("/api/camera-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cameraAction", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /camera-actions : Updates an existing cameraAction.
     *
     * @param cameraAction the cameraAction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cameraAction,
     * or with status 400 (Bad Request) if the cameraAction is not valid,
     * or with status 500 (Internal Server Error) if the cameraAction couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/camera-actions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CameraAction> updateCameraAction(@RequestBody CameraAction cameraAction) throws URISyntaxException {
        log.debug("REST request to update CameraAction : {}", cameraAction);
        if (cameraAction.getId() == null) {
            return createCameraAction(cameraAction);
        }
        CameraAction result = cameraActionService.save(cameraAction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cameraAction", cameraAction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /camera-actions : get all the cameraActions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cameraActions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/camera-actions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CameraAction>> getAllCameraActions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CameraActions");
        Page<CameraAction> page = cameraActionService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/camera-actions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /camera-actions/:id : get the "id" cameraAction.
     *
     * @param id the id of the cameraAction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cameraAction, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/camera-actions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CameraAction> getCameraAction(@PathVariable Long id) {
        log.debug("REST request to get CameraAction : {}", id);
        CameraAction cameraAction = cameraActionService.findOne(id);
        return Optional.ofNullable(cameraAction)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /camera-actions/:id : delete the "id" cameraAction.
     *
     * @param id the id of the cameraAction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/camera-actions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCameraAction(@PathVariable Long id) {
        log.debug("REST request to delete CameraAction : {}", id);
        cameraActionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cameraAction", id.toString())).build();
    }

}
