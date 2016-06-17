package nl.tudelft.thefirstorder.web.rest;

import ch.qos.logback.classic.LoggerContext;
import nl.tudelft.thefirstorder.ThefirstorderApp;
import nl.tudelft.thefirstorder.domain.Camera;
import nl.tudelft.thefirstorder.domain.Cue;
import nl.tudelft.thefirstorder.domain.Player;
import nl.tudelft.thefirstorder.repository.CueRepository;
import nl.tudelft.thefirstorder.service.CueService;
import nl.tudelft.thefirstorder.web.rest.dto.LoggerDTO;
import org.apache.log4j.spi.LoggerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


/**
 * Test class for the CueResource REST controller.
 *
 * @see CueResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ThefirstorderApp.class)
@WebAppConfiguration
@IntegrationTest
public class LoggerResourceIntTest {

    @Mock private LoggerDTO dto;
    private LogsResource logsResource = new LogsResource();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getListTest() {
        logsResource.getList();
    }

    @Test
    public void changeLevelTest() {
        when(dto.getName()).thenReturn("Name");
        when(dto.getLevel()).thenReturn("Level");
        logsResource.changeLevel(dto);
        verify(dto).getName();
        verify(dto).getLevel();
    }

}
