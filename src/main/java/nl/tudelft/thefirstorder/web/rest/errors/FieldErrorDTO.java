package nl.tudelft.thefirstorder.web.rest.errors;

import java.io.Serializable;

/**
 * DTO for sending a Field Error message
 */
public class FieldErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String objectName;

    private final String field;

    private final String message;

    /**
     * Constructor for Field Error DTO.
     * @param dto Data Transfer Object
     * @param field Field
     * @param message Message
     */
    public FieldErrorDTO(String dto, String field, String message) {
        this.objectName = dto;
        this.field = field;
        this.message = message;
    }

    /**
     * Get the object name of the field error.
     * @return the object name
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * Get the field of the field error.
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * Get the message of the field error.
     * @return the message
     */
    public String getMessage() {
        return message;
    }

}
