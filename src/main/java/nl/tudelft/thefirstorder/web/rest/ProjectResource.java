package nl.tudelft.thefirstorder.web.rest;

import com.codahale.metrics.annotation.Timed;
import nl.tudelft.thefirstorder.domain.Project;
import nl.tudelft.thefirstorder.service.ProjectService;
import nl.tudelft.thefirstorder.service.util.PDFExportUtil;
import nl.tudelft.thefirstorder.service.util.XMLExportUtil;
import nl.tudelft.thefirstorder.web.rest.util.HeaderUtil;
import nl.tudelft.thefirstorder.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Project.
 */
@RestController
@RequestMapping("/api")
public class ProjectResource {

    private final Logger log = LoggerFactory.getLogger(ProjectResource.class);

    @Inject
    private ProjectService projectService;

    /**
     * POST  /projects : Create a new project.
     *
     * @param project the project to create
     * @return the ResponseEntity with status 201 (Created) and with body the new project,
     *      or with status 400 (Bad Request) if the project has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/projects",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Project> createProject(@RequestBody Project project) throws URISyntaxException {
        log.debug("REST request to save Project : {}", project);
        if (project.getId() != null) {
            return ResponseEntity.badRequest().headers(
                    HeaderUtil.createFailureAlert("project", "idexists", "A new project cannot already have an ID")
            ).body(null);
        }
        Project result = projectService.save(project);
        return ResponseEntity.created(new URI("/api/projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("project", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /projects : Updates an existing project.
     *
     * @param project the project to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated project,
     *      or with status 400 (Bad Request) if the project is not valid,
     *      or with status 500 (Internal Server Error) if the project couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/projects",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Project> updateProject(@RequestBody Project project) throws URISyntaxException {
        log.debug("REST request to update Project : {}", project);
        if (project.getId() == null) {
            return createProject(project);
        }
        Project result = projectService.save(project);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("project", project.getId().toString()))
            .body(result);
    }

    /**
     * GET  /projects : get all the projects.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of projects in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/projects",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Project>> getAllProjects(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Projects");
        Page<Project> page = projectService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/projects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /projects/:id : get the "id" project.
     *
     * @param id the id of the project to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the project, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/projects/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        log.debug("REST request to get Project : {}", id);
        Project project = projectService.findOne(id);
        return Optional.ofNullable(project)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /projects/:id : delete the "id" project.
     *
     * @param id the id of the project to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/projects/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        log.debug("REST request to delete Project : {}", id);
        projectService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("project", id.toString())).build();
    }


    /**
     * GET /projects/:id/exportpdf : exports the project to a PDF.
     * Serve the PDF as download.
     * @param id the id of the project to export.
     * @return the ResponseEntity with status 200 (OK) or with status 404 (Not Found)
     * @throws IOException This exception is thrown when the resource cannot be read.
     */
    @RequestMapping(value = "/projects/{id}/exportpdf",
            method = RequestMethod.GET,
            produces = "application/pdf")
    @Timed
    public ResponseEntity<Resource> downloadPDF(@PathVariable Long id) throws IOException {
        log.debug("Request to get PDF download Project : {}", id);
        Optional<Project> project = Optional.ofNullable(projectService.findOne(id));

        // Disable cache for this response.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (!project.isPresent()) {
            log.error("Project {} could not be found.", id);
            return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
        }

        log.debug("Export Project {} to PDF.", id);
        Project currentProject = project.get();
        Resource resource = PDFExportUtil.exportProjectToPDF(currentProject);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    /**
     * GET /projects/:id/exportxml : exports the project to a XML.
     * Serve the XML as download.
     * @param id the id of the project to export.
     * @return the ResponseEntity with status 200 (OK) or with status 404 (Not Found)
     * @throws IOException This exception is thrown when the resource cannot be read.
     */
    @RequestMapping(value = "/projects/{id}/exportxml",
        method = RequestMethod.GET,
        produces = "application/xml")
    @Timed
    public ResponseEntity<Resource> downloadXML(@PathVariable Long id) throws IOException {
        log.debug("Request to get XML download Project : {}", id);
        Optional<Project> project = Optional.ofNullable(projectService.findOne(id));

        // Disable cache for this response.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        if (!project.isPresent()) {
            log.error("Project {} could not be found.", id);
            return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
        }

        log.debug("Export Project {} to XML.", id);
        Project currentProject = project.get();
        Resource resource = XMLExportUtil.export(currentProject);

        return ResponseEntity.ok()
            .headers(headers)
            .contentLength(resource.contentLength())
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);
    }
}
