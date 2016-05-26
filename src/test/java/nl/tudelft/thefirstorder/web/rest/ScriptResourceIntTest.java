package nl.tudelft.thefirstorder.web.rest;

import nl.tudelft.thefirstorder.ThefirstorderApp;
import nl.tudelft.thefirstorder.domain.Script;
import nl.tudelft.thefirstorder.repository.ScriptRepository;
import nl.tudelft.thefirstorder.service.ScriptService;

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
 * Test class for the ScriptResource REST controller.
 *
 * @see ScriptResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ThefirstorderApp.class)
@WebAppConfiguration
@IntegrationTest
public class ScriptResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private ScriptRepository scriptRepository;

    @Inject
    private ScriptService scriptService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restScriptMockMvc;

    private Script script;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ScriptResource scriptResource = new ScriptResource();
        ReflectionTestUtils.setField(scriptResource, "scriptService", scriptService);
        this.restScriptMockMvc = MockMvcBuilders.standaloneSetup(scriptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        script = new Script();
        script.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createScript() throws Exception {
        int databaseSizeBeforeCreate = scriptRepository.findAll().size();

        // Create the Script

        restScriptMockMvc.perform(post("/api/scripts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(script)))
                .andExpect(status().isCreated());

        // Validate the Script in the database
        List<Script> scripts = scriptRepository.findAll();
        assertThat(scripts).hasSize(databaseSizeBeforeCreate + 1);
        Script testScript = scripts.get(scripts.size() - 1);
        assertThat(testScript.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createScriptWithId() throws Exception {
        int databaseSizeBeforeCreate = scriptRepository.findAll().size();

        script.setId(123L);

        // Create the Script
        restScriptMockMvc.perform(post("/api/scripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(script)))
            .andExpect(status().isBadRequest());

        // Validate the Script is not in the database
        List<Script> scripts = scriptRepository.findAll();
        assertThat(scripts).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void updateScriptNoId() throws Exception {
        int databaseSizeBeforeUpdate = scriptRepository.findAll().size();

        // Update the Script
        Script updatedScript = new Script();
        updatedScript.setName("FooScript");

        restScriptMockMvc.perform(put("/api/scripts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedScript)))
            .andExpect(status().isCreated());

        // Validate the Script in the database
        List<Script> scripts = scriptRepository.findAll();
        assertThat(scripts).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void getAllScripts() throws Exception {
        // Initialize the database
        scriptRepository.saveAndFlush(script);

        // Get all the scripts
        restScriptMockMvc.perform(get("/api/scripts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(script.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getScript() throws Exception {
        // Initialize the database
        scriptRepository.saveAndFlush(script);

        // Get the script
        restScriptMockMvc.perform(get("/api/scripts/{id}", script.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(script.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingScript() throws Exception {
        // Get the script
        restScriptMockMvc.perform(get("/api/scripts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScript() throws Exception {
        // Initialize the database
        scriptService.save(script);

        int databaseSizeBeforeUpdate = scriptRepository.findAll().size();

        // Update the script
        Script updatedScript = new Script();
        updatedScript.setId(script.getId());
        updatedScript.setName(UPDATED_NAME);

        restScriptMockMvc.perform(put("/api/scripts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedScript)))
                .andExpect(status().isOk());

        // Validate the Script in the database
        List<Script> scripts = scriptRepository.findAll();
        assertThat(scripts).hasSize(databaseSizeBeforeUpdate);
        Script testScript = scripts.get(scripts.size() - 1);
        assertThat(testScript.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteScript() throws Exception {
        // Initialize the database
        scriptService.save(script);

        int databaseSizeBeforeDelete = scriptRepository.findAll().size();

        // Get the script
        restScriptMockMvc.perform(delete("/api/scripts/{id}", script.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Script> scripts = scriptRepository.findAll();
        assertThat(scripts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
