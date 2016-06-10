package nl.tudelft.thefirstorder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn
    private Project project;

    @Column(name = "action")
    private String action;

    @Column(name = "bar")
    private Integer bar;

    @Column(name = "duration")
    private Integer duration;

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
     * Getter for the project.
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Setter for the project.
     * @param project the project
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Getter for the action.
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * Setter for the action.
     * @param action the action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Getter for the bar.
     * @return the bar number
     */
    public Integer getBar() {
        return bar;
    }

    /**
     * Setter for the bar.
     * @param bar the bar number to set
     */
    public void setBar(Integer bar) {
        this.bar = bar;
    }

    /**
     * Getter for the duration of the cue.
     * @return the duration
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Setter for the duration of the cue.
     * @param duration the duration to set
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
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
