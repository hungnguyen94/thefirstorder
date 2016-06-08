package nl.tudelft.thefirstorder.web.rest;

import com.codahale.metrics.annotation.Timed;
import nl.tudelft.thefirstorder.domain.Camera;
import nl.tudelft.thefirstorder.service.CameraService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Camera.
 */
@RestController
@RequestMapping("/api")
public class CameraResource {

    private final Logger log = LoggerFactory.getLogger(CameraResource.class);

    @Inject
    private CameraService cameraService;

    /**
     * POST  /cameras : Create a new camera.
     *
     * @param camera the camera to create
     * @return the ResponseEntity with status 201 (Created) and with body the new camera,
     *      or with status 400 (Bad Request) if the camera has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cameras",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Camera> createCamera(@RequestBody Camera camera) throws URISyntaxException {
        log.debug("REST request to save Camera : {}", camera);
        if (camera.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil
                .createFailureAlert("camera", "idexists", "A new camera cannot already have an ID")).body(null);
        }
        Camera result = cameraService.save(camera);
        return ResponseEntity.created(new URI("/api/cameras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("camera", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cameras : Updates an existing camera.
     *
     * @param camera the camera to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated camera,
     *      or with status 400 (Bad Request) if the camera is not valid,
     *      or with status 500 (Internal Server Error) if the camera couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cameras",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Camera> updateCamera(@RequestBody Camera camera) throws URISyntaxException {
        log.debug("REST request to update Camera : {}", camera);
        if (camera.getId() == null) {
            return createCamera(camera);
        }
        Camera result = cameraService.save(camera);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("camera", camera.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cameras : get all the cameras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cameras in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/cameras",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Camera>> getAllCameras(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Cameras");
        Page<Camera> page = cameraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cameras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cameras/:id : get the "id" camera.
     *
     * @param id the id of the camera to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the camera, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/cameras/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Camera> getCamera(@PathVariable Long id) {
        log.debug("REST request to get Camera : {}", id);
        Camera camera = cameraService.findOne(id);
        return Optional.ofNullable(camera)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cameras/:id : delete the "id" camera.
     *
     * @param id the id of the camera to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/cameras/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCamera(@PathVariable Long id) {
        log.debug("REST request to delete Camera : {}", id);
        cameraService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("camera", id.toString())).build();
    }

}
