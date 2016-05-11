package nl.tudelft.thefirstorder.web.rest.errors;

import java.io.Serializable;

/**
 * DTO for sending a parameterized error message.
 */
public class ParameterizedErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String message;
    private final String[] params;

    /**
     * Constructor.
     * @param message the message
     * @param params the parameter
     */
    public ParameterizedErrorDTO(String message, String... params) {
        this.message = message;
        this.params = params;
    }

    /**
     * Get the message of a parameterized error.
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get the parameter of a parameterized error.
     * @return the parameter
     */
    public String[] getParams() {
        return params;
    }

}
