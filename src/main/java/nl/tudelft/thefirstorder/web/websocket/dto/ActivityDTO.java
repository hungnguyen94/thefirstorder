package nl.tudelft.thefirstorder.web.websocket.dto;

/**
 * DTO for storing a user's activity.
 */
public class ActivityDTO {

    private String sessionId;

    private String userLogin;

    private String ipAddress;

    private String page;

    private String time;

    /**
     * Get the session id of the DTO.
     * @return the session id
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Set the session id of the DTO.
     * @param sessionId the session id
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Get the user login of the DTO.
     * @return the user login
     */
    public String getUserLogin() {
        return userLogin;
    }

    /**
     * Set the user login of the DTO.
     * @param userLogin the user login
     */
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    /**
     * Get the ip address of the DTO.
     * @return the ip address
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Set the ip address of the DTO.
     * @param ipAddress the ip address
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Get the page of the DTO.
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * Set the page of the DTO.
     * @param page the page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * Get the time of the DTO.
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * Set the time of the DTO.
     * @param time the time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Represents the DTO as a string.
     * @return the string
     */
    @Override
    public String toString() {
        return "ActivityDTO{"
                + "sessionId='" + sessionId + '\''
                + ", userLogin='" + userLogin + '\''
                + ", ipAddress='" + ipAddress + '\''
                + ", page='" + page + '\''
                + ", time='" + time + '\'' + '}';
    }
}
