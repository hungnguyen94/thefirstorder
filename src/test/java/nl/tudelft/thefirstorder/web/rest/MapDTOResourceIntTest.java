package nl.tudelft.thefirstorder.web.rest;

import nl.tudelft.thefirstorder.ThefirstorderApp;
import nl.tudelft.thefirstorder.domain.Camera;
import nl.tudelft.thefirstorder.domain.Map;
import nl.tudelft.thefirstorder.domain.Player;
import nl.tudelft.thefirstorder.repository.CameraRepository;
import nl.tudelft.thefirstorder.repository.MapRepository;
import nl.tudelft.thefirstorder.repository.PlayerRepository;
import nl.tudelft.thefirstorder.service.MapService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Test class for the MapResource REST controller.
 *
 * @see MapResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ThefirstorderApp.class)
@WebAppConfiguration
@IntegrationTest
public class MapDTOResourceIntTest {

    private static final String DEFAULT_MAP_NAME = "AAAAA";
    private static final String UPDATED_MAP_NAME = "BBBBB";
    private static final String DEFAULT_CAMERA_NAME = "CCCCC";
    private static final String DEFAULT_PLAYER_NAME = "DDDDD";

    @Inject
    private MapRepository mapRepository;

    @Inject
    private PlayerRepository playerRepository;

    @Inject
    private CameraRepository cameraRepository;

    @Inject
    private MapService mapService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMapMockMvc;

    private Map map;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MapResource mapResource = new MapResource();
        ReflectionTestUtils.setField(mapResource, "mapService", mapService);
        this.restMapMockMvc = MockMvcBuilders.standaloneSetup(mapResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        map = new Map();
        map.setName(DEFAULT_MAP_NAME);
    }

    @Test
    @Transactional
    public void addCamera() throws Exception {
        Camera camera = new Camera();
        camera.setName(DEFAULT_CAMERA_NAME);

        // Initialize the database
        cameraRepository.saveAndFlush(camera);
        mapRepository.saveAndFlush(map);
        assertThat(map.getCameras()).doesNotContain(camera);

        map.addCamera(camera);
        cameraRepository.saveAndFlush(camera);
        assertThat(map.getCameras()).contains(camera);

        // Get the map
        restMapMockMvc.perform(get("/api/maps/{id}/dto", map.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(DEFAULT_MAP_NAME))
                .andExpect(jsonPath("$.cameras.[*].name").value(hasItem(DEFAULT_CAMERA_NAME)));
        assertThat(map.getCameras()).contains(camera);
    }

    @Test
    @Transactional
    public void addPlayer() throws Exception {
        Player player = new Player();
        player.setName(DEFAULT_PLAYER_NAME);

        // Initialize the database
        playerRepository.saveAndFlush(player);
        mapRepository.saveAndFlush(map);
        assertThat(map.getPlayers()).doesNotContain(player);

        map.addPlayer(player);
        mapRepository.saveAndFlush(map);
        assertThat(map.getPlayers()).contains(player);

        // Get the map
        restMapMockMvc.perform(get("/api/maps/{id}/dto", map.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(DEFAULT_MAP_NAME))
                .andExpect(jsonPath("$.players.[*].name").value(hasItem(DEFAULT_PLAYER_NAME)));
        assertThat(map.getPlayers()).contains(player);
    }

    @Test
    @Transactional
    public void getMapDTOWithInvalidMap() throws Exception {
        // Initialize the database
        mapRepository.saveAndFlush(map);

        // Get the map
        restMapMockMvc.perform(get("/api/maps/{id}/dto", map.getId() + 1L))
                .andExpect(status().isNotFound());
    }
}
