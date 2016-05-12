package nl.tudelft.thefirstorder.web.rest.errors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for transfering error message with a list of field errors.
 */
public class ErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String message;
    private final String description;

    private List<FieldErrorDTO> fieldErrors;

    public ErrorDTO(String message) {
        this(message, null);
    }

    /**
     * Constructor for ErrorDTO.
     * @param message Message
     * @param description Description
     */
    public ErrorDTO(String message, String description) {
        this.message = message;
        this.description = description;
    }

    /**
     * Constructor for ErrorDTO.
     * @param message Message
     * @param description Description
     * @param fieldErrors Field Errors
     */
    public ErrorDTO(String message, String description, List<FieldErrorDTO> fieldErrors) {
        this.message = message;
        this.description = description;
        this.fieldErrors = fieldErrors;
    }

    /**
     * Add a new fieldErrorDTO to the list of fieldErrors.
     * @param objectName Object name
     * @param field Field
     * @param message Message
     */
    public void add(String objectName, String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new FieldErrorDTO(objectName, field, message));
    }

    /**
     * Get the message of the error.
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get the description of the error.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the fielderror.
     * @return the fielderror
     */
    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }
}
