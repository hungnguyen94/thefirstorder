package nl.tudelft.thefirstorder.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
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

    @Column(name = "background_image")
    private String background_image;

    @OneToOne(mappedBy = "map")
    @JsonIgnore
    private Project project;

    @OneToMany(mappedBy = "map")
    @JsonIgnore
    private Set<Camera> cameras = new HashSet<>();

    @OneToMany(mappedBy = "map")
    @JsonIgnore
    private Set<Player> players = new HashSet<>();

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
     * Returns the location of the background image in the form of n url.
     * @return an url pointing to the location of the background image.
     */
    public String getBackgroundImage() {
        return background_image;
    }

    /**
     * Sets the location of the background image to the given url.
     * @param url should be a url representing the location of an image
     */
    public void setBackgroundImage(String url) {
        this.background_image = url;
    }

    /**
     * Set the name of the map.
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    public Set<Camera> getCameras() {
        return cameras;
    }

    public void setCameras(Set<Camera> cameras) {
        this.cameras = cameras;
    }

    /**
     * Adds a camera to the map.
     * @param camera Camera
     */
    public void addCamera(Camera camera) {
        cameras.add(camera);
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    /**
     * Adds a player to the map.
     * @param player Player
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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
                + ", background_image='" + background_image + "'"
                + '}';
    }
}
