package nl.tudelft.thefirstorder.web.rest;

import nl.tudelft.thefirstorder.ThefirstorderApp;
import nl.tudelft.thefirstorder.domain.Map;
import nl.tudelft.thefirstorder.repository.MapRepository;
import nl.tudelft.thefirstorder.service.MapService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the MapResource REST controller.
 *
 * @see MapResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ThefirstorderApp.class)
@WebAppConfiguration
@IntegrationTest
public class MapResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_BACKGROUND_IMAGE = "AAAAA";
    private static final String UPDATED_BACKGROUND_IMAGE = "BBBBB";

    @Inject
    private MapRepository mapRepository;

    @Inject
    private MapService mapService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMapMockMvc;

    private Map map;

    private MapResource mapResource;

    @Mock
    private Pageable pageable;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mapResource = new MapResource();
        ReflectionTestUtils.setField(mapResource, "mapService", mapService);
        this.restMapMockMvc = MockMvcBuilders.standaloneSetup(mapResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        map = new Map();
        map.setName(DEFAULT_NAME);
        map.setBackground_image(DEFAULT_BACKGROUND_IMAGE);
    }

    @Test
    @Transactional
    public void createMap() throws Exception {
        int databaseSizeBeforeCreate = mapRepository.findAll().size();

        // Create the Map

        restMapMockMvc.perform(post("/api/maps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(map)))
                .andExpect(status().isCreated());

        // Validate the Map in the database
        List<Map> maps = mapRepository.findAll();
        assertThat(maps).hasSize(databaseSizeBeforeCreate + 1);
        Map testMap = maps.get(maps.size() - 1);
        assertThat(testMap.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMap.getBackground_image()).isEqualTo(DEFAULT_BACKGROUND_IMAGE);
    }

    @Test
    @Transactional
    public void createMapWithId() throws Exception {
        int databaseSizeBeforeCreate = mapRepository.findAll().size();

        map.setId(123L);

        // Create the Cue
        restMapMockMvc.perform(post("/api/maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(map)))
            .andExpect(status().isBadRequest());

        // Validate the Cue is not in the database
        List<Map> maps = mapRepository.findAll();
        assertThat(maps).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateMapNoId() throws Exception {
        int databaseSizeBeforeUpdate = mapRepository.findAll().size();

        // Update the cue
        Map updatedMap = new Map();

        restMapMockMvc.perform(put("/api/maps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMap)))
            .andExpect(status().isCreated());

        // Validate the Cue in the database
        List<Map> maps = mapRepository.findAll();
        assertThat(maps).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void getAllMapsWhereProjectIsNull() throws Exception {
        assertThat(mapResource.getAllMaps(pageable,"project-is-null"))
            .isEqualTo(new ResponseEntity<>(mapService.findAllWhereProjectIsNull(),
            HttpStatus.OK));
    }

    @Test
    @Transactional
    public void getAllMaps() throws Exception {
        // Initialize the database
        mapRepository.saveAndFlush(map);

        // Get all the maps
        restMapMockMvc.perform(get("/api/maps?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(map.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].background_image").value(hasItem(DEFAULT_BACKGROUND_IMAGE.toString())));
    }

    @Test
    @Transactional
    public void getMap() throws Exception {
        // Initialize the database
        mapRepository.saveAndFlush(map);

        // Get the map
        restMapMockMvc.perform(get("/api/maps/{id}", map.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(map.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.background_image").value(DEFAULT_BACKGROUND_IMAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMap() throws Exception {
        // Get the map
        restMapMockMvc.perform(get("/api/maps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMap() throws Exception {
        // Initialize the database
        mapService.save(map);

        int databaseSizeBeforeUpdate = mapRepository.findAll().size();

        // Update the map
        Map updatedMap = new Map();
        updatedMap.setId(map.getId());
        updatedMap.setName(UPDATED_NAME);
        updatedMap.setBackground_image(UPDATED_BACKGROUND_IMAGE);

        restMapMockMvc.perform(put("/api/maps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedMap)))
                .andExpect(status().isOk());

        // Validate the Map in the database
        List<Map> maps = mapRepository.findAll();
        assertThat(maps).hasSize(databaseSizeBeforeUpdate);
        Map testMap = maps.get(maps.size() - 1);
        assertThat(testMap.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMap.getBackground_image()).isEqualTo(UPDATED_BACKGROUND_IMAGE);
    }

    @Test
    @Transactional
    public void deleteMap() throws Exception {
        // Initialize the database
        mapService.save(map);

        int databaseSizeBeforeDelete = mapRepository.findAll().size();

        // Get the map
        restMapMockMvc.perform(delete("/api/maps/{id}", map.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Map> maps = mapRepository.findAll();
        assertThat(maps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
