package nl.tudelft.thefirstorder.web.rest;

import nl.tudelft.thefirstorder.ThefirstorderApp;
import nl.tudelft.thefirstorder.domain.Camera;
import nl.tudelft.thefirstorder.repository.CameraRepository;
import nl.tudelft.thefirstorder.service.CameraService;

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
 * Test class for the CameraResource REST controller.
 *
 * @see CameraResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ThefirstorderApp.class)
@WebAppConfiguration
@IntegrationTest
public class CameraResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_X = 1;
    private static final Integer UPDATED_X = 2;

    private static final Integer DEFAULT_Y = 1;
    private static final Integer UPDATED_Y = 2;
    private static final String DEFAULT_CAMERA_TYPE = "AAAAA";
    private static final String UPDATED_CAMERA_TYPE = "BBBBB";
    private static final String DEFAULT_LENS_TYPE = "AAAAA";
    private static final String UPDATED_LENS_TYPE = "BBBBB";

    @Inject
    private CameraRepository cameraRepository;

    @Inject
    private CameraService cameraService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCameraMockMvc;

    private Camera camera;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CameraResource cameraResource = new CameraResource();
        ReflectionTestUtils.setField(cameraResource, "cameraService", cameraService);
        this.restCameraMockMvc = MockMvcBuilders.standaloneSetup(cameraResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        camera = new Camera();
        camera.setName(DEFAULT_NAME);
        camera.setX(DEFAULT_X);
        camera.setY(DEFAULT_Y);
        camera.setCameraType(DEFAULT_CAMERA_TYPE);
        camera.setLensType(DEFAULT_LENS_TYPE);
    }

    @Test
    @Transactional
    public void createCamera() throws Exception {
        int databaseSizeBeforeCreate = cameraRepository.findAll().size();

        // Create the Camera

        restCameraMockMvc.perform(post("/api/cameras")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(camera)))
                .andExpect(status().isCreated());

        // Validate the Camera in the database
        List<Camera> cameras = cameraRepository.findAll();
        assertThat(cameras).hasSize(databaseSizeBeforeCreate + 1);
        Camera testCamera = cameras.get(cameras.size() - 1);
        assertThat(testCamera.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCamera.getX()).isEqualTo(DEFAULT_X);
        assertThat(testCamera.getY()).isEqualTo(DEFAULT_Y);
        assertThat(testCamera.getCameraType()).isEqualTo(DEFAULT_CAMERA_TYPE);
        assertThat(testCamera.getLensType()).isEqualTo(DEFAULT_LENS_TYPE);
    }

    @Test
    @Transactional
    public void getAllCameras() throws Exception {
        // Initialize the database
        cameraRepository.saveAndFlush(camera);

        // Get all the cameras
        restCameraMockMvc.perform(get("/api/cameras?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(camera.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].x").value(hasItem(DEFAULT_X)))
                .andExpect(jsonPath("$.[*].y").value(hasItem(DEFAULT_Y)))
                .andExpect(jsonPath("$.[*].cameraType").value(hasItem(DEFAULT_CAMERA_TYPE.toString())))
                .andExpect(jsonPath("$.[*].lensType").value(hasItem(DEFAULT_LENS_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getCamera() throws Exception {
        // Initialize the database
        cameraRepository.saveAndFlush(camera);

        // Get the camera
        restCameraMockMvc.perform(get("/api/cameras/{id}", camera.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(camera.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.x").value(DEFAULT_X))
            .andExpect(jsonPath("$.y").value(DEFAULT_Y))
            .andExpect(jsonPath("$.cameraType").value(DEFAULT_CAMERA_TYPE.toString()))
            .andExpect(jsonPath("$.lensType").value(DEFAULT_LENS_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCamera() throws Exception {
        // Get the camera
        restCameraMockMvc.perform(get("/api/cameras/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCamera() throws Exception {
        // Initialize the database
        cameraService.save(camera);

        int databaseSizeBeforeUpdate = cameraRepository.findAll().size();

        // Update the camera
        Camera updatedCamera = new Camera();
        updatedCamera.setId(camera.getId());
        updatedCamera.setName(UPDATED_NAME);
        updatedCamera.setX(UPDATED_X);
        updatedCamera.setY(UPDATED_Y);
        updatedCamera.setCameraType(UPDATED_CAMERA_TYPE);
        updatedCamera.setLensType(UPDATED_LENS_TYPE);

        restCameraMockMvc.perform(put("/api/cameras")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCamera)))
                .andExpect(status().isOk());

        // Validate the Camera in the database
        List<Camera> cameras = cameraRepository.findAll();
        assertThat(cameras).hasSize(databaseSizeBeforeUpdate);
        Camera testCamera = cameras.get(cameras.size() - 1);
        assertThat(testCamera.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCamera.getX()).isEqualTo(UPDATED_X);
        assertThat(testCamera.getY()).isEqualTo(UPDATED_Y);
        assertThat(testCamera.getCameraType()).isEqualTo(UPDATED_CAMERA_TYPE);
        assertThat(testCamera.getLensType()).isEqualTo(UPDATED_LENS_TYPE);
    }

    @Test
    @Transactional
    public void deleteCamera() throws Exception {
        // Initialize the database
        cameraService.save(camera);

        int databaseSizeBeforeDelete = cameraRepository.findAll().size();

        // Get the camera
        restCameraMockMvc.perform(delete("/api/cameras/{id}", camera.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Camera> cameras = cameraRepository.findAll();
        assertThat(cameras).hasSize(databaseSizeBeforeDelete - 1);
    }
}
