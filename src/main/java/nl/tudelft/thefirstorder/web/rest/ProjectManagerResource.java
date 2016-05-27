package nl.tudelft.thefirstorder.web.rest;

import nl.tudelft.thefirstorder.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Optional;

/**
 * REST controller for managing the current user's account.
 * @author Hung
 */

@RestController
@RequestMapping("/api")
public class ProjectManagerResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    @Inject
    private UserService userService;

    /**
     * GET  /account/currentproject : get the current project the user is working on.
     *
     * @return ResponseEntity with status 200 (OK) and with the project id in the body,
     *      or with status 404 (Not Found)
     */
    @RequestMapping(value = "/account/currentproject",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Long> getCurrentProjectId() {
        return Optional.ofNullable(userService.getUserProjectId())
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK ))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * PUT  /users/currentproject : update the current project the user is working on.
     *
     * @return ResponseEntity with status 200 (OK) and with the project id in the body,
     *      or with status 404 (Not Found)
     */
    @RequestMapping(value = "/account/update_currentproject",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Long> setCurrentProjectId(@RequestParam Long projectId) {
        log.debug("REST request to update current project ID of current user to : {}", projectId);
        userService.updateUserProjectId(projectId);
        Long currentProjectId = userService.getUserProjectId();
        return new ResponseEntity<>(currentProjectId, HttpStatus.OK);
    }
}
