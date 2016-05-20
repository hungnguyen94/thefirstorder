package nl.tudelft.thefirstorder.web.rest;

import nl.tudelft.thefirstorder.ThefirstorderApp;
import nl.tudelft.thefirstorder.domain.CameraAction;
import nl.tudelft.thefirstorder.repository.CameraActionRepository;
import nl.tudelft.thefirstorder.service.CameraActionService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CameraActionResource REST controller.
 *
 * @see CameraActionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ThefirstorderApp.class)
@WebAppConfiguration
@IntegrationTest
public class CameraActionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private CameraActionRepository cameraActionRepository;

    @Inject
    private CameraActionService cameraActionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCameraActionMockMvc;

    private CameraAction cameraAction;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CameraActionResource cameraActionResource = new CameraActionResource();
        ReflectionTestUtils.setField(cameraActionResource, "cameraActionService", cameraActionService);
        this.restCameraActionMockMvc = MockMvcBuilders.standaloneSetup(cameraActionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cameraAction = new CameraAction();
        cameraAction.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCameraAction() throws Exception {
        int databaseSizeBeforeCreate = cameraActionRepository.findAll().size();

        // Create the CameraAction

        restCameraActionMockMvc.perform(post("/api/camera-actions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cameraAction)))
                .andExpect(status().isCreated());

        // Validate the CameraAction in the database
        List<CameraAction> cameraActions = cameraActionRepository.findAll();
        assertThat(cameraActions).hasSize(databaseSizeBeforeCreate + 1);
        CameraAction testCameraAction = cameraActions.get(cameraActions.size() - 1);
        assertThat(testCameraAction.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllCameraActions() throws Exception {
        // Initialize the database
        cameraActionRepository.saveAndFlush(cameraAction);

        // Get all the cameraActions
        restCameraActionMockMvc.perform(get("/api/camera-actions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cameraAction.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCameraAction() throws Exception {
        // Initialize the database
        cameraActionRepository.saveAndFlush(cameraAction);

        // Get the cameraAction
        restCameraActionMockMvc.perform(get("/api/camera-actions/{id}", cameraAction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cameraAction.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCameraAction() throws Exception {
        // Get the cameraAction
        restCameraActionMockMvc.perform(get("/api/camera-actions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCameraAction() throws Exception {
        // Initialize the database
        cameraActionService.save(cameraAction);

        int databaseSizeBeforeUpdate = cameraActionRepository.findAll().size();

        // Update the cameraAction
        CameraAction updatedCameraAction = new CameraAction();
        updatedCameraAction.setId(cameraAction.getId());
        updatedCameraAction.setName(UPDATED_NAME);

        restCameraActionMockMvc.perform(put("/api/camera-actions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCameraAction)))
                .andExpect(status().isOk());

        // Validate the CameraAction in the database
        List<CameraAction> cameraActions = cameraActionRepository.findAll();
        assertThat(cameraActions).hasSize(databaseSizeBeforeUpdate);
        CameraAction testCameraAction = cameraActions.get(cameraActions.size() - 1);
        assertThat(testCameraAction.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteCameraAction() throws Exception {
        // Initialize the database
        cameraActionService.save(cameraAction);

        int databaseSizeBeforeDelete = cameraActionRepository.findAll().size();

        // Get the cameraAction
        restCameraActionMockMvc.perform(delete("/api/camera-actions/{id}", cameraAction.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CameraAction> cameraActions = cameraActionRepository.findAll();
        assertThat(cameraActions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
