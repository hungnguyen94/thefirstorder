package nl.tudelft.thefirstorder.web.rest;

import nl.tudelft.thefirstorder.ThefirstorderApp;
import nl.tudelft.thefirstorder.domain.Map;
import nl.tudelft.thefirstorder.repository.MapRepository;
import nl.tudelft.thefirstorder.service.MapService;

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
 * Test class for the MapResource REST controller.
 *
 * @see MapResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ThefirstorderApp.class)
@WebAppConfiguration
@IntegrationTest
public class MapResourceIntTest {


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
                .andExpect(jsonPath("$.[*].id").value(hasItem(map.getId().intValue())));
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
            .andExpect(jsonPath("$.id").value(map.getId().intValue()));
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

        restMapMockMvc.perform(put("/api/maps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedMap)))
                .andExpect(status().isOk());

        // Validate the Map in the database
        List<Map> maps = mapRepository.findAll();
        assertThat(maps).hasSize(databaseSizeBeforeUpdate);
        Map testMap = maps.get(maps.size() - 1);
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
