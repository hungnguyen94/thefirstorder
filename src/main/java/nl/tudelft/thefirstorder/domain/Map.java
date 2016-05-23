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
 * A Map.
 */
@Entity
@Table(name = "map")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Map implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    /**
     * Get the id of the map.
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id of the map.
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the name of the map.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the map.
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if two maps are the same.
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
        Map map = (Map) o;
        if (map.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, map.id);
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
     * Represents the map as a string.
     * @return the string
     */
    @Override
    public String toString() {
        return "Map{" + "id=" + id
                + ", name='" + name + "'"
                + '}';
    }
}
