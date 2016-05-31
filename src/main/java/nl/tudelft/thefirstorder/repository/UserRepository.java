package nl.tudelft.thefirstorder.repository;

import nl.tudelft.thefirstorder.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find an optional user via an activation key.
     *
     * @param activationKey the key
     * @return an optional user
     */
    Optional<User> findOneByActivationKey(String activationKey);

    /**
     * Find all users which are not activated and are created before a certain date.
     *
     * @param dateTime the date
     * @return the list of users
     */
    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(ZonedDateTime dateTime);

    /**
     * Find an optional user via a reset key.
     *
     * @param resetKey the key
     * @return an optional user
     */
    Optional<User> findOneByResetKey(String resetKey);

    /**
     * Find an optional user via a email address.
     *
     * @param email the address
     * @return an optional user
     */
    Optional<User> findOneByEmail(String email);

    /**
     * Find an optional user via a login string.
     *
     * @param login the login string
     * @return an optional user
     */
    Optional<User> findOneByLogin(String login);

    /**
     * Find an optional user via a user ID.
     *
     * @param userId the user ID
     * @return an optional user
     */
    Optional<User> findOneById(Long userId);

    /**
     * Find all users which have the project with given project id as their current project
     *
     * @param currentProjectId the user ID
     * @return a list of users
     */
    List<User> findAllByCurrentProjectId(Long currentProjectId);

    /**
     * Remove a user.
     *
     * @param t the user to remove
     */
    @Override
    void delete(User t);

}
