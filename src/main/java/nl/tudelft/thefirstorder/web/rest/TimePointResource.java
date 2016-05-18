package nl.tudelft.thefirstorder.web.rest;

import com.codahale.metrics.annotation.Timed;
import nl.tudelft.thefirstorder.domain.TimePoint;
import nl.tudelft.thefirstorder.service.TimePointService;
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
 * REST controller for managing TimePoint.
 */
@RestController
@RequestMapping("/api")
public class TimePointResource {

    private final Logger log = LoggerFactory.getLogger(TimePointResource.class);
        
    @Inject
    private TimePointService timePointService;
    
    /**
     * POST  /time-points : Create a new timePoint.
     *
     * @param timePoint the timePoint to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timePoint,
     *     or with status 400 (Bad Request) if the timePoint has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/time-points",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimePoint> createTimePoint(@RequestBody TimePoint timePoint) throws URISyntaxException {
        log.debug("REST request to save TimePoint : {}", timePoint);
        if (timePoint.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(
                    "timePoint", "idexists", "A new timePoint cannot already have an ID")).body(null);
        }
        TimePoint result = timePointService.save(timePoint);
        return ResponseEntity.created(new URI("/api/time-points/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("timePoint", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /time-points : Updates an existing timePoint.
     *
     * @param timePoint the timePoint to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timePoint,
     *      or with status 400 (Bad Request) if the timePoint is not valid,
     *      or with status 500 (Internal Server Error) if the timePoint couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/time-points",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimePoint> updateTimePoint(@RequestBody TimePoint timePoint) throws URISyntaxException {
        log.debug("REST request to update TimePoint : {}", timePoint);
        if (timePoint.getId() == null) {
            return createTimePoint(timePoint);
        }
        TimePoint result = timePointService.save(timePoint);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("timePoint", timePoint.getId().toString()))
            .body(result);
    }

    /**
     * GET  /time-points : get all the timePoints.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of timePoints in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/time-points",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TimePoint>> getAllTimePoints(Pageable pageable,
                                                            @RequestParam(required = false) String filter)
        throws URISyntaxException {
        if ("cue-is-null".equals(filter)) {
            log.debug("REST request to get all TimePoints where cue is null");
            return new ResponseEntity<>(timePointService.findAllWhereCueIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of TimePoints");
        Page<TimePoint> page = timePointService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/time-points");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /time-points/:id : get the "id" timePoint.
     *
     * @param id the id of the timePoint to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timePoint, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/time-points/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimePoint> getTimePoint(@PathVariable Long id) {
        log.debug("REST request to get TimePoint : {}", id);
        TimePoint timePoint = timePointService.findOne(id);
        return Optional.ofNullable(timePoint)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /time-points/:id : delete the "id" timePoint.
     *
     * @param id the id of the timePoint to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/time-points/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTimePoint(@PathVariable Long id) {
        log.debug("REST request to delete TimePoint : {}", id);
        timePointService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("timePoint", id.toString())).build();
    }

}
