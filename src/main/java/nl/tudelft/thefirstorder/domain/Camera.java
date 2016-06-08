package nl.tudelft.thefirstorder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Camera.
 */
@Entity
@Table(name = "camera")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Camera implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Map map;

    @Column(name = "name")
    private String name;

    @Column(name = "x")
    private Integer x;

    @Column(name = "y")
    private Integer y;

    @Column(name = "camera_type")
    private String cameraType;

    @Column(name = "lens_type")
    private String lensType;

    public Long getId() {
        return id;
    }

    /**
     * Set the id of the camera.
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the name of the camera.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the camera.
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the x position of the camera.
     * @return the x position
     */
    public Integer getX() {
        return x;
    }

    /**
     * Set the x position of the camera.
     * @param x the x position
     */
    public void setX(Integer x) {
        this.x = x;
    }

    /**
     * Get the y position of the camera.
     * @return the y position
     */
    public Integer getY() {
        return y;
    }

    /**
     * Set the y position of the camera.
     * @param y the y position
     */
    public void setY(Integer y) {
        this.y = y;
    }

    public String getCameraType() {
        return cameraType;
    }

    public void setCameraType(String cameraType) {
        this.cameraType = cameraType;
    }

    public String getLensType() {
        return lensType;
    }

    public void setLensType(String lensType) {
        this.lensType = lensType;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Checks if two cameras are the same.
     * @param o the camera to compare with
     * @return a boolean as result
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Camera camera = (Camera) o;
        if (camera.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, camera.id);
    }

    /**
     * Generates a hashcode.
     * @return the hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * Represent the camera as a string.
     * @return a string
     */
    @Override
    public String toString() {
        return "Camera{"
            + "id=" + id + ", name='" + name + "'"
            + ", x='" + x + "'" + ", y='" + y + "'"
            + ", cameraType='" + cameraType + "'"
            + ", lensType='" + lensType + "'" + '}';
    }
}
