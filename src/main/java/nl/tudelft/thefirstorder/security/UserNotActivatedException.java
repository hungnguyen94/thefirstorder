package nl.tudelft.thefirstorder.security;

import org.springframework.security.core.AuthenticationException;

/**
 * This exception is throw in case of a not activated user trying to authenticate.
 */
public class UserNotActivatedException extends AuthenticationException {

    /**
     * Constructor.
     * @param message the message
     */
    public UserNotActivatedException(String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param message the message
     * @param t a throwable exception
     */
    public UserNotActivatedException(String message, Throwable t) {
        super(message, t);
    }
}
