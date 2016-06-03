package nl.tudelft.thefirstorder.web.rest;

import nl.tudelft.thefirstorder.ThefirstorderApp;
import nl.tudelft.thefirstorder.domain.Camera;
import nl.tudelft.thefirstorder.domain.Cue;
import nl.tudelft.thefirstorder.domain.Player;
import nl.tudelft.thefirstorder.repository.CueRepository;
import nl.tudelft.thefirstorder.service.CueService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CueResource REST controller.
 *
 * @see CueResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ThefirstorderApp.class)
@WebAppConfiguration
@IntegrationTest
public class CueResourceIntTest {


    @Inject
    private CueRepository cueRepository;

    @Inject
    private CueService cueService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCueMockMvc;

    private Cue cue;

    private static final String DEFAULT_ACTION = "AAAAA";
    private static final String UPDATED_ACTION = "BBBBB";
    private static final Integer DEFAULT_BAR = 1;
    private static final Integer UPDATED_BAR = 3;
    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 3;

    @Mock
    private Player player;

    @Mock
    private Camera camera;

    private CueResource cueResource;

    @Mock private Pageable pageable;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        cueResource = new CueResource();
        ReflectionTestUtils.setField(cueResource, "cueService", cueService);
        this.restCueMockMvc = MockMvcBuilders.standaloneSetup(cueResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cue = new Cue();
    }

    @Test
    @Transactional
    public void createCue() throws Exception {
        int databaseSizeBeforeCreate = cueRepository.findAll().size();

        // Create the Cue

        restCueMockMvc.perform(post("/api/cues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cue)))
                .andExpect(status().isCreated());

        // Validate the Cue in the database
        List<Cue> cues = cueRepository.findAll();
        assertThat(cues).hasSize(databaseSizeBeforeCreate + 1);
        Cue testCue = cues.get(cues.size() - 1);
        assertThat(testCue.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testCue.getBar()).isEqualTo(DEFAULT_BAR);
        assertThat(testCue.getDuration()).isEqualTo(DEFAULT_DURATION);
    }

    @Test
    @Transactional
    public void createCueWithId() throws Exception {
        int databaseSizeBeforeCreate = cueRepository.findAll().size();

        cue.setId(123L);

        // Create the Cue
        restCueMockMvc.perform(post("/api/cues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cue)))
            .andExpect(status().isBadRequest());

        // Validate the Cue is not in the database
        List<Cue> cues = cueRepository.findAll();
        assertThat(cues).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCues() throws Exception {
        // Initialize the database
        cueRepository.saveAndFlush(cue);

        // Get all the cues
        restCueMockMvc.perform(get("/api/cues?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cue.getId().intValue())))
                .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION.toString())))
                .andExpect(jsonPath("$.[*].bar").value(hasItem(DEFAULT_BAR.intValue())))
                .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.intValue())));
    }

    @Test
    public void getAllCuesNotNullId() throws Exception {
        assertThat(cueResource.getAllCues(pageable,new Long(1))).isEqualTo(ResponseEntity.ok(cueService.findCuesByScript(new Long(1))));
    }

    @Test
    @Transactional
    public void getCameraFromCue() throws Exception {
        // Initialize the database
        cueRepository.saveAndFlush(cue);

        cue.setCamera(camera);
        assertThat(cue.getCamera()).isEqualTo(camera);

        assertThat(cueService.getCamera(cue.getId())).isEqualTo(camera);
    }

    @Test
    @Transactional
    public void getPlayerFromCue() throws Exception {
        // Initialize the database
        cueRepository.saveAndFlush(cue);

        cue.setPlayer(player);
        assertThat(cue.getPlayer()).isEqualTo(player);

        assertThat(cueService.getPlayer(cue.getId())).isEqualTo(player);
    }

    @Test
    @Transactional
    public void getCue() throws Exception {
        // Initialize the database
        cueRepository.saveAndFlush(cue);

        // Get the cue
        restCueMockMvc.perform(get("/api/cues/{id}", cue.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(cue.getId().intValue()))
                .andExpect(jsonPath("$.action").value(DEFAULT_ACTION.toString()))
                .andExpect(jsonPath("$.bar").value(DEFAULT_BAR.intValue()))
                .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCue() throws Exception {
        // Get the cue
        restCueMockMvc.perform(get("/api/cues/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCue() throws Exception {
        // Initialize the database
        cueService.save(cue);

        int databaseSizeBeforeUpdate = cueRepository.findAll().size();

        // Update the cue
        Cue updatedCue = new Cue();
        updatedCue.setId(cue.getId());

        restCueMockMvc.perform(put("/api/cues")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCue)))
                .andExpect(status().isOk());

        // Validate the Cue in the database
        List<Cue> cues = cueRepository.findAll();
        assertThat(cues).hasSize(databaseSizeBeforeUpdate);
        Cue testCue = cues.get(cues.size() - 1);
        assertThat(testCue.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testCue.getBar()).isEqualTo(UPDATED_BAR);
        assertThat(testCue.getDuration()).isEqualTo(UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void updateCueNoId() throws Exception {
        int databaseSizeBeforeUpdate = cueRepository.findAll().size();

        // Update the cue
        Cue updatedCue = new Cue();

        restCueMockMvc.perform(put("/api/cues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCue)))
            .andExpect(status().isCreated());

        // Validate the Cue in the database
        List<Cue> cues = cueRepository.findAll();
        assertThat(cues).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCue() throws Exception {
        // Initialize the database
        cueService.save(cue);

        int databaseSizeBeforeDelete = cueRepository.findAll().size();

        // Get the cue
        restCueMockMvc.perform(delete("/api/cues/{id}", cue.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cue> cues = cueRepository.findAll();
        assertThat(cues).hasSize(databaseSizeBeforeDelete - 1);
    }
}
