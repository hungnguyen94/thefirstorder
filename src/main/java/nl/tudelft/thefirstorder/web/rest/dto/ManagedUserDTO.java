package nl.tudelft.thefirstorder.web.rest.dto;

import nl.tudelft.thefirstorder.domain.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * A DTO extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserDTO extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;
    public static final int PASSWORD_MAX_LENGTH = 100;

    private Long id;

    private ZonedDateTime createdDate;

    private String lastModifiedBy;

    private ZonedDateTime lastModifiedDate;

    @NotNull
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    /**
     * Constructor.
     */
    public ManagedUserDTO() {
    }

    /**
     * Constructor for ManagedUserDTO.
     * @param user User
     */
    public ManagedUserDTO(User user) {
        super(user);
        this.id = user.getId();
        this.createdDate = user.getCreatedDate();
        this.lastModifiedBy = user.getLastModifiedBy();
        this.lastModifiedDate = user.getLastModifiedDate();
        this.password = null;
    }

    /**
     * Constructor for ManagedUserDTO.
     * @param id Id
     * @param login Login
     * @param password Password
     * @param firstName Firstname
     * @param lastName Lastname
     * @param email Email
     * @param activated Is the account activated?
     * @param langKey Language key
     * @param authorities Authorities
     * @param createdDate Created date
     * @param lastModifiedBy Last modified by
     * @param lastModifiedDate Last modified date
     */
    public ManagedUserDTO(Long id,
                          String login,
                          String password,
                          String firstName,
                          String lastName,
                          String email,
                          boolean activated,
                          String langKey,
                          Set<String> authorities,
                          ZonedDateTime createdDate,
                          String lastModifiedBy,
                          ZonedDateTime lastModifiedDate ) {
        super(login, firstName, lastName, email, activated, langKey, authorities);
        this.id = id;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.password = password;
    }

    /**
     * Get the id of the DTO.
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id of the DTO.
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the creation date of the DTO.
     * @return the creation date
     */
    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Set the creation date of the DTO
     * @param createdDate the creation date
     */
    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Get the user who last modified the DTO.
     * @return the user
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * Set the user who last modified the DTO.
     * @param lastModifiedBy the user
     */
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /**
     * Get the date when the DTO is last modified.
     * @return the date
     */
    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Set the date when the DTO is last modified.
     * @param lastModifiedDate the date
     */
    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     * Get the password of the DTO.
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Represents the DTO as a string.
     * @return the string
     */
    @Override
    public String toString() {
        return "ManagedUserDTO{"
                + "id=" + id
                + ", createdDate=" + createdDate
                + ", lastModifiedBy='" + lastModifiedBy + '\''
                + ", lastModifiedDate=" + lastModifiedDate
                + "} "
                + super.toString();
    }
}
