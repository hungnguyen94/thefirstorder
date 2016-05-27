package nl.tudelft.thefirstorder.web.rest;

import nl.tudelft.thefirstorder.ThefirstorderApp;
import nl.tudelft.thefirstorder.domain.Authority;
import nl.tudelft.thefirstorder.domain.User;
import nl.tudelft.thefirstorder.repository.AuthorityRepository;
import nl.tudelft.thefirstorder.repository.UserRepository;
import nl.tudelft.thefirstorder.security.AuthoritiesConstants;
import nl.tudelft.thefirstorder.service.MailService;
import nl.tudelft.thefirstorder.service.UserService;
import nl.tudelft.thefirstorder.web.rest.dto.ManagedUserDTO;
import nl.tudelft.thefirstorder.web.rest.dto.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Equals;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProjectManager class.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ThefirstorderApp.class)
@WebAppConfiguration
@IntegrationTest
public class ProjectManagerResourceIntTest {

    @Inject
    private UserRepository userRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private UserService userService;

    @Mock
    private UserService mockUserService;

    @Mock
    private MailService mockMailService;

    private MockMvc restUserMockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        doNothing().when(mockMailService).sendActivationEmail((User) anyObject(), anyString());

        ProjectManagerResource projectManagerResource = new ProjectManagerResource();
        ReflectionTestUtils.setField(projectManagerResource, "userService", mockUserService);

        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(projectManagerResource).build();
    }

    @Test
    public void testUpdateProjectIdOfCurrentUser() throws Exception {
        Set<Authority> authorities = new HashSet<>();
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.ADMIN);
        authorities.add(authority);

        Long projectId = 123L;
        User user = new User();
        user.setLogin("johndoe");
        user.setFirstName("john");
        user.setLastName("doe");
        user.setAuthorities(authorities);
        user.setCurrentProjectId(321L);
        when(mockUserService.getUserWithAuthorities()).thenReturn(user);

        restUserMockMvc.perform(put("/api/account/update_currentproject")
                .param("projectId", String.valueOf(projectId)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProjectIdOfCurrentUser() throws Exception {
        Set<Authority> authorities = new HashSet<>();
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.ADMIN);
        authorities.add(authority);

        Long currentProjectId = 123L;
        User user = new User();
        user.setLogin("test");
        user.setFirstName("john");
        user.setLastName("doe");
        user.setAuthorities(authorities);
        user.setCurrentProjectId(currentProjectId);
        when(mockUserService.getUserWithAuthorities()).thenReturn(user);
        when(mockUserService.getUserProjectId()).thenReturn(currentProjectId);

        MvcResult result = restUserMockMvc.perform(get("/api/account/currentproject")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo(String.valueOf(currentProjectId));
    }
}
