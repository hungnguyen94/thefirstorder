package nl.tudelft.thefirstorder.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nl.tudelft.thefirstorder.models.Cue;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * A Script.
 */
@Entity
@Table(name = "script")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Script implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "script")
    @JsonIgnore
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Script script = (Script) o;
        if(script.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, script.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Script{" +
            "id=" + id +
            '}';
    }

    private List<Cue> cues;

    /**
     * Create a Script.
     * @param cues The cues which are already in the script
     */
    public Script(List<Cue> cues) {
        this.cues = cues;
    }

    /**
     * Get the list of cues.
     * @return the list of cues
     */
    public List<Cue> getCues() {
        return cues;
    }

    /**
     * Add a cue to the end of the script.
     * @param cue the cue which has to be added
     */
    public void addLast(Cue cue) {
        cues.add(cue);
    }

    /**
     * Add a cue to the begin of the script.
     * @param cue the cue which has to be added
     */
    public void addFirst(Cue cue) {
        cues.add(0,cue);
    }

    /**
     * Remove a cue from the script.
     * @param cue the cue which has to be removed
     */
    public void removeCue(Cue cue) {
        cues.remove(cue);
    }

    public void removeAllCues() {
        cues = new LinkedList<Cue>();
    }

    /**
     * Add a cue in after a particular cue.
     * @param cueBefore the cue after which one has to be added
     * @param cueAfter the cue which has to be added
     */
    public void addAfter(Cue cueBefore, Cue cueAfter) {
        List<Cue> result = new LinkedList<Cue>();
        int i = cues.indexOf(cueBefore);
        if(i == -1) {
            addFirst(cueAfter);
        } else {
            cues.add(i, cueAfter);
        }
    }
}
