package nl.tudelft.thefirstorder.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @Column(name = "name")
    private String name;

    @Column(name = "score")
    private String score;

    @OneToOne(mappedBy = "script")
    @JsonIgnore
    private Project project;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "script")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cue> cues = new HashSet<>();

    /**
     * Get the id of the script.
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id of the script.
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the name of the script.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the script.
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the cues of the script.
     * @return the cues
     */
    public Set<Cue> getCues() {
        return cues;
    }

    /**
     * Set the cues of the script.
     * @param cues the cues
     */
    public void setCues(Set<Cue> cues) {
        this.cues = cues;
    }

    /**
     * Get the project which contains this script.
     * @return the project which contains this script
     */
    public Project getProject() {
        return project;
    }

    /**
     * Set the project to contain this script.
     * @param project should be the new project to contain this script
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Get the score of this script.
     * @return the score of this script
     */
    public String getScore() {
        return score;
    }

    /**
     * Set the score of this script.
     * @param score the new score of this script
     */
    public void setScore(String score) {
        this.score = score;
    }

    /**
     * Checks if two scripts are the same.
     * @param o the object to compare with
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
        Script script = (Script) o;
        if (script.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, script.id);
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
     * Represents a script as a string.
     * @return the string
     */
    @Override
    public String toString() {
        return "Script{"
            + "id=" + id
            + ", name='" + name
            + "'" + '}';
    }
}
