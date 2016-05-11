package nl.tudelft.thefirstorder.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[a-z0-9]*$|(anonymousUser)")
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash",length = 60)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @NotNull
    @Email
    @Size(max = 100)
    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 5)
    @Column(name = "lang_key", length = 5)
    private String langKey;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    private String resetKey;

    @Column(name = "reset_date", nullable = true)
    private ZonedDateTime resetDate = null;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Authority> authorities = new HashSet<>();

    /**
     * Get the id of the user.
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id of the user.
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the login string of the user.
     * @return the login string
     */
    public String getLogin() {
        return login;
    }

    /**
     * Set the login string of the user.
     * @param login the login string
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Get the password of the user.
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the user.
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the first name of the user.
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the first name of the user.
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the last name of the user.
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the last name of the user.
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the email of the user.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email of the user.
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get whether the user is activated or not.
     * @return the result as a boolean
     */
    public boolean getActivated() {
        return activated;
    }

    /**
     * Set whether the user is activated or not.
     * @param activated a boolean
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    /**
     * Get the activation key of the user.
     * @return the activation key
     */
    public String getActivationKey() {
        return activationKey;
    }

    /**
     * Set the activation key of the user.
     * @param activationKey the activation key
     */
    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    /**
     * Get the reset key of the user.
     * @return the reset key
     */
    public String getResetKey() {
        return resetKey;
    }

    /**
     * Set the reset key of the user.
     * @param resetKey the reset key
     */
    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    /**
     * Get the reset date of the user.
     * @return the reset date
     */
    public ZonedDateTime getResetDate() {
        return resetDate;
    }

    /**
     * Set the reset date of the user.
     * @param resetDate the reset date
     */
    public void setResetDate(ZonedDateTime resetDate) {
        this.resetDate = resetDate;
    }

    /**
     * Get the lang key of the user.
     * @return the lang key
     */
    public String getLangKey() {
        return langKey;
    }

    /**
     * Set the lang key of the user.
     * @param langKey the lang key
     */
    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    /**
     * Get the list of authorities of the user.
     * @return the list of authorities
     */
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    /**
     * Set the authorities of the user.
     * @param authorities the list of authorities
     */
    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    /**
     * Check whether two users are the same.
     * @param o the object to compare with
     * @return the result as a boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (!login.equals(user.login)) {
            return false;
        }

        return true;
    }

    /**
     * Generates a hash code.
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return login.hashCode();
    }

    /**
     * Represents the user as a string.
     * @return the string
     */
    @Override
    public String toString() {
        return "User{"
            + "login='" + login + '\''
            + ", firstName='" + firstName + '\''
            + ", lastName='" + lastName + '\''
            + ", email='" + email + '\''
            + ", activated='" + activated + '\''
            + ", langKey='" + langKey + '\''
            + ", activationKey='" + activationKey + '\''
            + "}";
    }
}
