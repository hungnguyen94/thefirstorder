package nl.tudelft.thefirstorder.service;

import nl.tudelft.thefirstorder.domain.Authority;
import nl.tudelft.thefirstorder.domain.User;
import nl.tudelft.thefirstorder.repository.AuthorityRepository;
import nl.tudelft.thefirstorder.repository.UserRepository;
import nl.tudelft.thefirstorder.security.SecurityUtils;
import nl.tudelft.thefirstorder.service.util.RandomUtil;
import nl.tudelft.thefirstorder.web.rest.dto.ManagedUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);


    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserRepository userRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    /**
     * Activate an user by a key, if that key is in the database.
     * @param key Key as a String.
     * @return The corresponding user to that key.
     */
    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                userRepository.save(user);
                log.debug("Activated user: {}", user);
                return user;
            });
    }

    /**
     * Resets the password of an user, given the key.
     * @param newPassword The new password for this user
     * @param key Key as a String
     * @return The user.
     */
    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);

        return userRepository.findOneByResetKey(key)
                .filter(user -> {
                    ZonedDateTime oneDayAgo = ZonedDateTime.now().minusHours(24);
                    return user.getResetDate().isAfter(oneDayAgo);
                })
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetKey(null);
                    user.setResetDate(null);
                    userRepository.save(user);
                    return user;
                });
    }

    /**
     * Request a password reset by finding the user with his email.
     * @param mail Email address of user.
     * @return User, if that email is found in the database.
     */
    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmail(mail)
            .filter(User::getActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(ZonedDateTime.now());
                userRepository.save(user);
                return user;
            });
    }

    /**
     * Creates an user.
     * @param login Login name
     * @param password Password
     * @param firstName First name
     * @param lastName Last name
     * @param email Email address
     * @param langKey Langkey
     * @return The created user.
     */
    public User createUserInformation(String login, String password, String firstName, String lastName, String email,
        String langKey) {

        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(login);
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setLangKey(langKey);
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());

        Authority authority = authorityRepository.findOne("ROLE_USER");
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    /**
     * Creates an user given a user data transfer object.
     * @param managedUserDto Managed user data transfer object.
     * @return The user.
     */
    public User createUser(ManagedUserDTO managedUserDto) {
        User user = new User();
        user.setLogin(managedUserDto.getLogin());
        user.setFirstName(managedUserDto.getFirstName());
        user.setLastName(managedUserDto.getLastName());
        user.setEmail(managedUserDto.getEmail());
        if (managedUserDto.getLangKey() == null) {
            user.setLangKey("en"); // default language
        } else {
            user.setLangKey(managedUserDto.getLangKey());
        }
        if (managedUserDto.getAuthorities() != null) {
            Set<Authority> authorities = new HashSet<>();
            managedUserDto.getAuthorities().stream().forEach(
                authority -> authorities.add(authorityRepository.findOne(authority))
            );
            user.setAuthorities(authorities);
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(ZonedDateTime.now());
        user.setActivated(true);
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Updates the current logged in user information.
     * @param firstName First name
     * @param lastName Last name
     * @param email Email
     * @param langKey Langkey
     */
    public void updateUserInformation(String firstName, String lastName, String email, String langKey) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(u -> {
            u.setFirstName(firstName);
            u.setLastName(lastName);
            u.setEmail(email);
            u.setLangKey(langKey);
            userRepository.save(u);
            log.debug("Changed Information for User: {}", u);
        });
    }

    /**
     * Deletes an user by it's login name.
     * @param login Login name.
     */
    public void deleteUserInformation(String login) {
        userRepository.findOneByLogin(login).ifPresent(u -> {
            userRepository.delete(u);
            log.debug("Deleted User: {}", u);
        });
    }

    /**
     * Changes the password of the current logged in user.
     * @param password New password.
     */
    public void changePassword(String password) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(u -> {
            String encryptedPassword = passwordEncoder.encode(password);
            u.setPassword(encryptedPassword);
            userRepository.save(u);
            log.debug("Changed password for User: {}", u);
        });
    }

    /**
     * Get user with authority by login name, if found.
     * @param login Login name
     * @return User wrapped in Optional
     */
    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneByLogin(login).map(u -> {
            u.getAuthorities().size();
            return u;
        });
    }

    /**
     * Get user with authorities.
     * @param id Id
     * @return User with authorities.
     */
    @Transactional(readOnly = true)
    public User getUserWithAuthorities(Long id) {
        User user = userRepository.findOne(id);
        user.getAuthorities().size(); // eagerly load the association
        return user;
    }

    /**
     * Get user with authorities.
     * @return User with authorities.
     */
    @Transactional(readOnly = true)
    public User getUserWithAuthorities() {
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        user.getAuthorities().size(); // eagerly load the association
        return user;
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     * </p>
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        ZonedDateTime now = ZonedDateTime.now();
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(now.minusDays(3));
        for (User user : users) {
            log.debug("Deleting not activated user {}", user.getLogin());
            userRepository.delete(user);
        }
    }

    /**
     * Get the current project id this user is working on.
     * @return Project id
     */
    @Transactional(readOnly = true)
    public Long getUserProjectId() {
        User user = getUserWithAuthorities();
        return user.getCurrentProjectId();
    }

    /**
     * Update the current project id of the currently logged in User.
     * @param id Updated project id
     */
    public void updateUserProjectId(Long id) {
        User user = getUserWithAuthorities();
        user.setCurrentProjectId(id);
        userRepository.save(user);
        log.debug("Changed current project ID to {} for User: {}", id, user);
    }
}
