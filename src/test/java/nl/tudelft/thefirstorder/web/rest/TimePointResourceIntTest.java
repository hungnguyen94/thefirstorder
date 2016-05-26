package nl.tudelft.thefirstorder.web.rest;

import nl.tudelft.thefirstorder.ThefirstorderApp;
import nl.tudelft.thefirstorder.domain.TimePoint;
import nl.tudelft.thefirstorder.repository.TimePointRepository;
import nl.tudelft.thefirstorder.service.TimePointService;

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
 * Test class for the TimePointResource REST controller.
 *
 * @see TimePointResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ThefirstorderApp.class)
@WebAppConfiguration
@IntegrationTest
public class TimePointResourceIntTest {


    private static final Integer DEFAULT_START_TIME = 1;
    private static final Integer UPDATED_START_TIME = 2;

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    @Inject
    private TimePointRepository timePointRepository;

    @Inject
    private TimePointService timePointService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTimePointMockMvc;

    private TimePoint timePoint;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TimePointResource timePointResource = new TimePointResource();
        ReflectionTestUtils.setField(timePointResource, "timePointService", timePointService);
        this.restTimePointMockMvc = MockMvcBuilders.standaloneSetup(timePointResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        timePoint = new TimePoint();
        timePoint.setStartTime(DEFAULT_START_TIME);
        timePoint.setDuration(DEFAULT_DURATION);
    }

    @Test
    @Transactional
    public void createTimePoint() throws Exception {
        int databaseSizeBeforeCreate = timePointRepository.findAll().size();

        // Create the TimePoint

        restTimePointMockMvc.perform(post("/api/time-points")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timePoint)))
                .andExpect(status().isCreated());

        // Validate the TimePoint in the database
        List<TimePoint> timePoints = timePointRepository.findAll();
        assertThat(timePoints).hasSize(databaseSizeBeforeCreate + 1);
        TimePoint testTimePoint = timePoints.get(timePoints.size() - 1);
        assertThat(testTimePoint.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testTimePoint.getDuration()).isEqualTo(DEFAULT_DURATION);
    }

    @Test
    @Transactional
    public void createTimePointWithId() throws Exception {
        int databaseSizeBeforeCreate = timePointRepository.findAll().size();

        timePoint.setId(123L);

        // Create the TimePoint
        restTimePointMockMvc.perform(post("/api/time-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timePoint)))
            .andExpect(status().isBadRequest());

        // Validate the TimePoint is not in the database
        List<TimePoint> timePoints = timePointRepository.findAll();
        assertThat(timePoints).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateTimePointNoId() throws Exception {
        int databaseSizeBeforeUpdate = timePointRepository.findAll().size();

        // Update the TimePoint
        TimePoint updatedTimePoint = new TimePoint();

        restTimePointMockMvc.perform(put("/api/time-points")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTimePoint)))
            .andExpect(status().isCreated());

        // Validate the TimePoint in the database
        List<TimePoint> timePoints = timePointRepository.findAll();
        assertThat(timePoints).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void getAllTimePoints() throws Exception {
        // Initialize the database
        timePointRepository.saveAndFlush(timePoint);

        // Get all the timePoints
        restTimePointMockMvc.perform(get("/api/time-points?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(timePoint.getId().intValue())))
                .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME)))
                .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)));
    }

    @Test
    @Transactional
    public void getTimePoint() throws Exception {
        // Initialize the database
        timePointRepository.saveAndFlush(timePoint);

        // Get the timePoint
        restTimePointMockMvc.perform(get("/api/time-points/{id}", timePoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(timePoint.getId().intValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION));
    }

    @Test
    @Transactional
    public void getNonExistingTimePoint() throws Exception {
        // Get the timePoint
        restTimePointMockMvc.perform(get("/api/time-points/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimePoint() throws Exception {
        // Initialize the database
        timePointService.save(timePoint);

        int databaseSizeBeforeUpdate = timePointRepository.findAll().size();

        // Update the timePoint
        TimePoint updatedTimePoint = new TimePoint();
        updatedTimePoint.setId(timePoint.getId());
        updatedTimePoint.setStartTime(UPDATED_START_TIME);
        updatedTimePoint.setDuration(UPDATED_DURATION);

        restTimePointMockMvc.perform(put("/api/time-points")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTimePoint)))
                .andExpect(status().isOk());

        // Validate the TimePoint in the database
        List<TimePoint> timePoints = timePointRepository.findAll();
        assertThat(timePoints).hasSize(databaseSizeBeforeUpdate);
        TimePoint testTimePoint = timePoints.get(timePoints.size() - 1);
        assertThat(testTimePoint.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTimePoint.getDuration()).isEqualTo(UPDATED_DURATION);
    }

    @Test
    @Transactional
    public void deleteTimePoint() throws Exception {
        // Initialize the database
        timePointService.save(timePoint);

        int databaseSizeBeforeDelete = timePointRepository.findAll().size();

        // Get the timePoint
        restTimePointMockMvc.perform(delete("/api/time-points/{id}", timePoint.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TimePoint> timePoints = timePointRepository.findAll();
        assertThat(timePoints).hasSize(databaseSizeBeforeDelete - 1);
    }
}
