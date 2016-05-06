package nl.tudelft.thefirstorder.web.rest.util;

import org.springframework.http.HttpHeaders;

/**
 * Utility class for HTTP headers creation.
 *
 */
public class HeaderUtil {

    /**
     * Create an alert.
     * @param message Message
     * @param param Parameters
     * @return HttpHeaders with alert
     */
    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-thefirstorderApp-alert", message);
        headers.add("X-thefirstorderApp-params", param);
        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert("A new " + entityName + " is created with identifier " + param, param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert("A " + entityName + " is updated with identifier " + param, param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert("A " + entityName + " is deleted with identifier " + param, param);
    }

    /**
     * Create a failure alert.
     * @param entityName Entity that gave the error
     * @param errorKey Key of the error
     * @param defaultMessage Default message
     * @return HTTP header with alert
     */
    public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-thefirstorderApp-error", defaultMessage);
        headers.add("X-thefirstorderApp-params", entityName);
        return headers;
    }
}
