package nl.tudelft.thefirstorder.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Player.
 */
@Entity
@Table(name = "player")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    /**
     * Get the id of the player.
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id of the player.
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the name of the player.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the player.
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if two players are the same.
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
        Player player = (Player) o;
        if(player.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, player.id);
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
     * Represents a player as a string.
     * @return the string
     */
    @Override
    public String toString() {
        return "Player{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
