package nl.tudelft.thefirstorder.web.rest.dto;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Logger DTO.
 */
public class LoggerDTO {

    private String name;

    private String level;

    /**
     * Constructor.
     * @param logger the logger
     */
    public LoggerDTO(Logger logger) {
        this.name = logger.getName();
        this.level = logger.getEffectiveLevel().toString();
    }

    /**
     * Constructor.
     */
    @JsonCreator
    public LoggerDTO() {
    }

    /**
     * Get the name of the DTO.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the DTO.
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the level of the DTO.
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * Set the level of the DTO.
     * @param level the level
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * Represents the DTO as a string.
     * @return the string
     */
    @Override
    public String toString() {
        return "LoggerDTO{"
                + "name='" + name + '\''
                + ", level='" + level + '\''
                + '}';
    }
}
