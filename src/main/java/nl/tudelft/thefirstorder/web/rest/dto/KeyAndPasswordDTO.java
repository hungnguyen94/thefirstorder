package nl.tudelft.thefirstorder.web.rest.dto;

/**
 * Key and password DTO.
 */
public class KeyAndPasswordDTO {

    private String key;
    private String newPassword;

    /**
     * Constructor.
     */
    public KeyAndPasswordDTO() {
    }

    /**
     * Get the key of the DTO.
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Set the key of the DTO.
     * @param key the key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Get the password of the DTO.
     * @return the password
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Set the password of the DTO.
     * @param newPassword the password
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
