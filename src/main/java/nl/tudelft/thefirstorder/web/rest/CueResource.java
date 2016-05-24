package nl.tudelft.thefirstorder.web.rest;

import com.codahale.metrics.annotation.Timed;
import nl.tudelft.thefirstorder.domain.Cue;
import nl.tudelft.thefirstorder.service.CueService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Cue.
 */
@RestController
@RequestMapping("/api")
public class CueResource {

    private final Logger log = LoggerFactory.getLogger(CueResource.class);
        
    @Inject
    private CueService cueService;
    
    /**
     * POST  /cues : Create a new cue.
     *
     * @param cue the cue to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cue,
     *      or with status 400 (Bad Request) if the cue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cues",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cue> createCue(@RequestBody Cue cue) throws URISyntaxException {
        log.debug("REST request to save Cue : {}", cue);
        if (cue.getId() != null) {
            return ResponseEntity.badRequest().headers(
                    HeaderUtil.createFailureAlert("cue", "idexists", "A new cue cannot already have an ID")
            ).body(null);
        }
        Cue result = cueService.save(cue);
        return ResponseEntity.created(new URI("/api/cues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cue", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cues : Updates an existing cue.
     *
     * @param cue the cue to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cue,
     *      or with status 400 (Bad Request) if the cue is not valid,
     *      or with status 500 (Internal Server Error) if the cue couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cues",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cue> updateCue(@RequestBody Cue cue) throws URISyntaxException {
        log.debug("REST request to update Cue : {}", cue);
        if (cue.getId() == null) {
            return createCue(cue);
        }
        Cue result = cueService.save(cue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cue", cue.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cues : get all the cues.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cues in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/cues",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Cue>> getAllCues(Pageable pageable,
                                                @RequestParam(required = false) Long projectId)
            throws URISyntaxException {
        if(projectId != null) {
            List<Cue> cues = cueService.findAllByProject(projectId);
            return ResponseEntity.ok(cues);
        }
        log.debug("REST request to get a page of Cues");
        Page<Cue> page = cueService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cues");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cues/:id : get the "id" cue.
     *
     * @param id the id of the cue to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cue, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/cues/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cue> getCue(@PathVariable Long id) {
        log.debug("REST request to get Cue : {}", id);
        Cue cue = cueService.findOne(id);
        return Optional.ofNullable(cue)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cues/:id : delete the "id" cue.
     *
     * @param id the id of the cue to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/cues/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCue(@PathVariable Long id) {
        log.debug("REST request to delete Cue : {}", id);
        cueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cue", id.toString())).build();
    }

}
