package nl.tudelft.thefirstorder.web.rest;

import nl.tudelft.thefirstorder.ThefirstorderApp;
import nl.tudelft.thefirstorder.domain.Cue;
import nl.tudelft.thefirstorder.domain.Map;
import nl.tudelft.thefirstorder.domain.Project;
import nl.tudelft.thefirstorder.domain.Script;
import nl.tudelft.thefirstorder.repository.ProjectRepository;
import nl.tudelft.thefirstorder.service.CueService;
import nl.tudelft.thefirstorder.service.MapService;
import nl.tudelft.thefirstorder.service.ProjectService;

import nl.tudelft.thefirstorder.service.ScriptService;
import nl.tudelft.thefirstorder.service.util.PDFExportUtil;
import nl.tudelft.thefirstorder.service.util.XMLExportUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasItem;

import static org.mockito.Mockito.when;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ProjectResource REST controller.
 *
 * @see ProjectResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ThefirstorderApp.class)
@WebAppConfiguration
@IntegrationTest
public class ProjectResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private ProjectService projectService;

    @Inject
    private MapService mapService;

    @Inject
    private ScriptService scriptService;

    @Inject
    private CueService cueService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProjectMockMvc;

    private Project project;

    private ProjectResource projectResource;

    @Mock private Script script;
    @Mock private Map map;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        projectResource = new ProjectResource();
        ReflectionTestUtils.setField(projectResource, "projectService", projectService);
        this.restProjectMockMvc = MockMvcBuilders.standaloneSetup(projectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        project = new Project();
        project.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProject() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        // Create the Project

        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isCreated());

        // Validate the Project in the database
        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(databaseSizeBeforeCreate + 1);
        Project testProject = projects.get(projects.size() - 1);
        assertThat(testProject.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProjectWithId() throws Exception {
        int databaseSizeBeforeCreate = projectRepository.findAll().size();

        project.setId(123L);

        // Create the Project
        restProjectMockMvc.perform(post("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(project)))
            .andExpect(status().isBadRequest());

        // Validate the Project is not in the database
        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateProjectNoId() throws Exception {
        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the Project
        Project updatedProject = new Project();

        restProjectMockMvc.perform(put("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProject)))
            .andExpect(status().isCreated());

        // Validate the Project in the database
        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void getAllProjects() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get all the projects
        restProjectMockMvc.perform(get("/api/projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(project.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getProject() throws Exception {
        // Initialize the database
        projectRepository.saveAndFlush(project);

        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}", project.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(project.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProject() throws Exception {
        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProject() throws Exception {
        // Initialize the database
        projectService.save(project);

        int databaseSizeBeforeUpdate = projectRepository.findAll().size();

        // Update the project
        Project updatedProject = new Project();
        updatedProject.setId(project.getId());
        updatedProject.setName(UPDATED_NAME);

        restProjectMockMvc.perform(put("/api/projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProject)))
            .andExpect(status().isOk());

        // Validate the Project in the database
        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(databaseSizeBeforeUpdate);
        Project testProject = projects.get(projects.size() - 1);
        assertThat(testProject.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteProject() throws Exception {
        // Initialize the database
        projectService.save(project);

        int databaseSizeBeforeDelete = projectRepository.findAll().size();

        // Get the project
        restProjectMockMvc.perform(delete("/api/projects/{id}", project.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void getMapTest() throws Exception {
        Map map = new Map();
        String mapName = "FooMap";
        map.setName(mapName);
        project.setMap(map);

        // Initialize the database
        mapService.save(map);
        projectService.save(project);

        // Get the project
        restProjectMockMvc.perform(get("/api/projects/{id}/map", project.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value(mapName.toString()));
    }

    @Test
    @Transactional
    public void downloadPDFNonExistingProjectTest() throws Exception {
        assertThat(projectResource.downloadPDF(new Long(200))).isEqualTo(new ResponseEntity<Resource>(HttpStatus.NOT_FOUND));
    }

    @Test
    @Transactional
    public void downloadXMLNonExistingProjectTest() throws Exception {
        assertThat(projectResource.downloadXML(new Long(200))).isEqualTo(new ResponseEntity<Resource>(HttpStatus.NOT_FOUND));
    }

    @Test
    @Transactional
    public void downloadXMLProjectTest() throws Exception {
        Project project = new Project();
        Script script = new Script();
        script.setName("AAA");
        project.setScript(script);
        projectRepository.save(project);
        Optional<Project> projectop = Optional.ofNullable(projectService.findOne(project.getId()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        Project currentProject = projectop.get();
        Resource resource = XMLExportUtil.exportProjectToXML(currentProject);
        assertThat(projectResource.downloadXML(project.getId())).isEqualTo(ResponseEntity.ok()
            .headers(headers)
            .contentLength(resource.contentLength())
            .contentType(MediaType.parseMediaType("application/xml"))
            .body(resource));
    }

    @Test
    @Transactional
    public void downloadPDFProjectTest() throws Exception {
        Project project = new Project();
        Script script = new Script();
        project.setScript(script);
        script.setName("Test");
        script.setCues(new HashSet<>());

        projectRepository.save(project);
        Optional<Project> projectop = Optional.ofNullable(projectService.findOne(project.getId()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        Project currentProject = projectop.get();
        Resource resource = PDFExportUtil.exportProjectToPDF(currentProject);
        projectResource.downloadPDF(project.getId());
    }
}
