package nl.tudelft.thefirstorder.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String USER = "ROLE_USER";
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    public static final String SITHLORD = "ROLE_SITHLORD";
    public static final String DIRECTOR = "ROLE_DIRECTOR";
    public static final String CAMERA_OPERATOR = "ROLE_CAMERA_OPERATOR";
    public static final String SCORE_READER = "ROLE_SCORE_READER";

    /**
     * Constructor.
     */
    private AuthoritiesConstants() {
    }
}
