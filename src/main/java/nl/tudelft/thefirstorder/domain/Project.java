package nl.tudelft.thefirstorder.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn
    private Script script;

    @OneToOne
    @JoinColumn
    private Map map;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cue> cues = new HashSet<>();

    /**
     * Get the id of the project.
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id of the project.
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the name of the project.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the project.
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the script of the project.
     * @return the script
     */
    public Script getScript() {
        return script;
    }

    /**
     * Set the script of the project.
     * @param script the script
     */
    public void setScript(Script script) {
        this.script = script;
    }

    /**
     * Get the map of the project.
     * @return the map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Set the map of the project.
     * @param map the map
     */
    public void setMap(Map map) {
        this.map = map;
    }

    public Set<Cue> getCues() {
        return cues;
    }

    public void setCues(Set<Cue> cues) {
        this.cues = cues;
    }

    /**
     * Checks if two projects are the same.
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
        Project project = (Project) o;
        if (project.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, project.id);
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
     * Represents a project as a string.
     * @return the string
     */
    @Override
    public String toString() {
        return "Project{"
                + "id=" + id
                + ", name='" + name
                + "'" + '}';
    }
}
