package nl.tudelft.thefirstorder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Camera Action.
 */
@Entity
@Table(name = "camera_action")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CameraAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private Integer duration;

    /**
     * Get the id of the camera action.
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id of the camera action.
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the name of the camera action.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the camera action.
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the duration of the camera action.
     * @return the duration
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Set the duration of the camera action.
     * @param duration the duration
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Checks if two camera actions are the same.
     * @param o the camera action to compare with
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
        CameraAction cameraAction = (CameraAction) o;
        if (cameraAction.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cameraAction.id);
    }

    /**
     * Generate a hashcode.
     * @return the hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * Represents the camera action as a string.
     * @return the string
     */
    @Override
    public String toString() {
        return "CameraAction{" + "id=" + id
                + ", name='" + name + "'"
                + ", duration='" + duration + "'"
                + '}';
    }
}
