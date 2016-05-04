package nl.tudelft.thefirstorder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    @Min(value = 0)
    @Column(name = "duration")
    private Double duration;

    @ManyToOne
    private Script script;

    @OneToOne
    @JoinColumn(unique = true)
    private Player player;

    @OneToOne
    @JoinColumn(unique = true)
    private Camera camera;

    @OneToOne
    @JoinColumn(unique = true)
    private CameraAction cameraAction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public CameraAction getCameraAction() {
        return cameraAction;
    }

    public void setCameraAction(CameraAction cameraAction) {
        this.cameraAction = cameraAction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cue cue = (Cue) o;
        if(cue.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cue.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cue{" +
            "id=" + id +
            ", duration='" + duration + "'" +
            '}';
    }
}
