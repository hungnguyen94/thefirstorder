package nl.tudelft.thefirstorder.web.rest.dto;

import nl.tudelft.thefirstorder.domain.Camera;
import nl.tudelft.thefirstorder.domain.Map;
import nl.tudelft.thefirstorder.domain.Player;

import java.util.Set;

/**
 * A DTO representing a map with all it's entities.
 */
public class MapDTO {

    private String name;

    private String background_image;

    private Set<Camera> cameras;

    private Set<Player> players;

    /**
     * Data transfer object for transfering Map entities.
     * @param map The map.
     */
    public MapDTO(Map map) {
        this.name = map.getName();
        this.background_image = map.getBackgroundImage();
        this.cameras = map.getCameras();
        this.players = map.getPlayers();
    }

    public Set<Camera> getCameras() {
        return cameras;
    }

    public void setCameras(Set<Camera> cameras) {
        this.cameras = cameras;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sbCamera = new StringBuilder();
        cameras.forEach(camera -> sbCamera.append(camera).append("\n"));
        StringBuilder sbPlayer = new StringBuilder();
        players.forEach(player -> sbPlayer.append(player).append("\n"));
        return "MapDTO{"
                + "cameras=" + "\n" + sbCamera.toString()
                + ", players=" + "\n" + sbPlayer.toString()
                + '}';
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
}
