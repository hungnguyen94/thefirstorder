package nl.tudelft.thefirstorder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Cue.
 */
@Entity
@Table(name = "cue")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Player player;

    @ManyToOne
    @JoinColumn
    private Camera camera;

    @ManyToOne
    @JoinColumn
    private Script script;

    @OneToOne
    @JoinColumn
    private CameraAction cameraAction;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private TimePoint timePoint;

    @ManyToOne
    @JoinColumn
    private Project project;

    /**
     * Get the id of the cue.
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id of the cue.
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Get the player of the cue.
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Set the player of the cue.
     * @param player the player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Get the camera action of the cue.
     * @return the camera action
     */
    public CameraAction getCameraAction() {
        return cameraAction;
    }

    /**
     * Set the camera action of the cue.
     * @param cameraAction the camera action
     */
    public void setCameraAction(CameraAction cameraAction) {
        this.cameraAction = cameraAction;
    }

    /**
     * Getter for TimePoint.
     * @return the TimePoint
     */
    public TimePoint getTimePoint() {
        return timePoint;
    }

    /**
     * Setter for TimePoint.
     * @param timePoint TimePoint to be set
     */
    public void setTimePoint(TimePoint timePoint) {
        this.timePoint = timePoint;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Checks if two cues are the same.
     * @param o the object to compare with
     * @return the result as boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cue cue = (Cue) o;
        return Objects.equals(id, cue.id);
    }

    /**
     * Generates a hash code.
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * Represents the cue as a string.
     * @return the string
     */
    @Override
    public String toString() {
        return "Cue{"
                + "id=" + id
                + '}';
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }
}
