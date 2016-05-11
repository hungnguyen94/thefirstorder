package nl.tudelft.thefirstorder.web.rest.dto;

import nl.tudelft.thefirstorder.domain.Authority;
import nl.tudelft.thefirstorder.domain.User;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    @Pattern(regexp = "^[a-z0-9]*$")
    @NotNull
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    private boolean activated = false;

    @Size(min = 2, max = 5)
    private String langKey;

    private Set<String> authorities;

    /**
     * Constructor.
     */
    public UserDTO() {
    }

    /**
     * Copy constructor for UserDTO.
     * @param user The User
     */
    public UserDTO(User user) {
        this(user.getLogin(), user.getFirstName(), user.getLastName(),
            user.getEmail(), user.getActivated(), user.getLangKey(),
            user.getAuthorities().stream().map(Authority::getName)
                .collect(Collectors.toSet()));
    }

    /**
     * Constructor for UserDTO.
     * @param login Login name
     * @param firstName First name
     * @param lastName Last name
     * @param email Email address
     * @param activated Is the user activated?
     * @param langKey Language key
     * @param authorities Authorities
     */
    public UserDTO(String login, String firstName, String lastName,
        String email, boolean activated, String langKey, Set<String> authorities) {

        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.activated = activated;
        this.langKey = langKey;
        this.authorities = authorities;
    }

    /**
     * Get the login string of the DTO.
     * @return the login string
     */
    public String getLogin() {
        return login;
    }

    /**
     * Get the first name of the DTO.
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get the last name of the DTO.
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get the email of the DTO.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get whether the DTO of the user is activated.
     * @return a boolean
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Get the lang key of the DTO.
     * @return the lang key
     */
    public String getLangKey() {
        return langKey;
    }

    /**
     * Get the authorities of the DTO
     * @return the authorities
     */
    public Set<String> getAuthorities() {
        return authorities;
    }

    /**
     * Represents the DTO as a string.
     * @return the string
     */
    @Override
    public String toString() {
        return "UserDTO{"
            + "login='" + login + '\''
            + ", firstName='" + firstName + '\''
            + ", lastName='" + lastName + '\''
            + ", email='" + email + '\''
            + ", activated=" + activated
            + ", authorities=" + authorities
            + "}";
    }
}
