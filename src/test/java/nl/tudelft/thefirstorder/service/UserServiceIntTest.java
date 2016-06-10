package nl.tudelft.thefirstorder.service;

import nl.tudelft.thefirstorder.ThefirstorderApp;
import nl.tudelft.thefirstorder.domain.User;
import nl.tudelft.thefirstorder.repository.UserRepository;
import java.time.ZonedDateTime;

import nl.tudelft.thefirstorder.security.SecurityUtils;
import nl.tudelft.thefirstorder.service.util.RandomUtil;
import java.time.LocalDate;

import nl.tudelft.thefirstorder.web.rest.dto.ManagedUserDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ThefirstorderApp.class)
@WebAppConfiguration
@IntegrationTest
@Transactional
public class UserServiceIntTest {

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    private ManagedUserDTO dto;
    private ManagedUserDTO dto2;

    @Before
    public void setUp() {
        dto = new ManagedUserDTO(new Long(1),
            "login",
            "Password",
            "First",
            "Last",
            "Email@email.com",
            true,
            "nl",
            new HashSet<>(),
            null,
            null,
            null);

        dto2 = new ManagedUserDTO(new Long(1),
            "login",
            "Password",
            "First",
            "Last",
            "Email@email.com",
            true,
            null,
            null,
            null,
            null,
            null);
    }

    @Test
    public void getUserWithAuthoritiesByLogin() {
        Optional<User> user = userService.getUserWithAuthoritiesByLogin("admin");
        assertThat(user.isPresent()).isTrue();
    }

    @Test
    public void getUserWithAuthoritiesById() {
        User user = userService.getUserWithAuthorities(new Long(1));
        assertThat(user.getLogin()).isEqualTo("system");
    }

    @Test
    public void changePassword() {
        userService.changePassword("Testpassword");
    }

    @Test
    public void updateUserInformation() {
        userService.updateUserInformation("TestFirst","TestLast","Testtest@test.nl","nl");
    }

    @Test
    public void deleteUserInformation() {
        userService.deleteUserInformation("admin");
        Optional<User> user = userRepository.findOneByLogin("admin");
        assertThat(user.isPresent()).isFalse();
    }


    @Test
    public void activateRegistration() {
        Optional<User> user = userService.activateRegistration("Test");
        assertThat(user.isPresent()).isFalse();

    }

    @Test
    public void createUserNotNullLangKeyAndAuthorities() {
        User user = userService.createUser(dto);
        assertThat(user.getFirstName()).isEqualTo("First");
        assertThat(user.getLastName()).isEqualTo("Last");
    }

    @Test
    public void createUserNullLangKeyAndAuthorities() {
        User user = userService.createUser(dto2);
        assertThat(user.getFirstName()).isEqualTo("First");
        assertThat(user.getLastName()).isEqualTo("Last");
        assertThat(user.getLangKey()).isEqualTo("en");
    }

    @Test
    public void assertThatUserMustExistToResetPassword() {
        Optional<User> maybeUser = userService.requestPasswordReset("john.doe@localhost");
        assertThat(maybeUser.isPresent()).isFalse();

        maybeUser = userService.requestPasswordReset("admin@localhost");
        assertThat(maybeUser.isPresent()).isTrue();

        assertThat(maybeUser.get().getEmail()).isEqualTo("admin@localhost");
        assertThat(maybeUser.get().getResetDate()).isNotNull();
        assertThat(maybeUser.get().getResetKey()).isNotNull();
    }

    @Test
    public void assertThatOnlyActivatedUserCanRequestPasswordReset() {
        User user = userService.createUserInformation("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "en-US");
        Optional<User> maybeUser = userService.requestPasswordReset("john.doe@localhost");
        assertThat(maybeUser.isPresent()).isFalse();
        userRepository.delete(user);
    }

    @Test
    public void assertThatResetKeyMustNotBeOlderThan24Hours() {
        User user = userService.createUserInformation("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "en-US");

        ZonedDateTime daysAgo = ZonedDateTime.now().minusHours(25);
        String resetKey = RandomUtil.generateResetKey();
        user.setActivated(true);
        user.setResetDate(daysAgo);
        user.setResetKey(resetKey);

        userRepository.save(user);

        Optional<User> maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey());

        assertThat(maybeUser.isPresent()).isFalse();

        userRepository.delete(user);
    }

    @Test
    public void assertThatResetKeyMustBeValid() {
        User user = userService.createUserInformation("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "en-US");

        ZonedDateTime daysAgo = ZonedDateTime.now().minusHours(25);
        user.setActivated(true);
        user.setResetDate(daysAgo);
        user.setResetKey("1234");
        userRepository.save(user);
        Optional<User> maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey());
        assertThat(maybeUser.isPresent()).isFalse();
        userRepository.delete(user);
    }

    @Test
    public void assertThatUserCanResetPassword() {
        User user = userService.createUserInformation("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "en-US");
        String oldPassword = user.getPassword();
        ZonedDateTime daysAgo = ZonedDateTime.now().minusHours(2);
        String resetKey = RandomUtil.generateResetKey();
        user.setActivated(true);
        user.setResetDate(daysAgo);
        user.setResetKey(resetKey);
        userRepository.save(user);
        Optional<User> maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey());
        assertThat(maybeUser.isPresent()).isTrue();
        assertThat(maybeUser.get().getResetDate()).isNull();
        assertThat(maybeUser.get().getResetKey()).isNull();
        assertThat(maybeUser.get().getPassword()).isNotEqualTo(oldPassword);

        userRepository.delete(user);
    }

    @Test
    public void testFindNotActivatedUsersByCreationDateBefore() {
        userService.removeNotActivatedUsers();
        ZonedDateTime now = ZonedDateTime.now();
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(now.minusDays(3));
        assertThat(users).isEmpty();
    }

    @Test
    public void assertThatCurrentProjectIdIsNull() {
        User user = userService.createUserInformation("johndoe", "johndoe", "John", "Doe", "john.doe@localhost", "en-US");
        assertThat(user.getCurrentProjectId()).isNull();
    }
}
