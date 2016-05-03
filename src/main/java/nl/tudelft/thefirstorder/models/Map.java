package nl.tudelft.thefirstorder.models;

import nl.tudelft.thefirstorder.models.cameras.Camera;
import nl.tudelft.thefirstorder.models.players.Player;

import java.util.List;

/**
 * A map interface. In the map, cameras and players are stored.
 */
public interface Map {

    /**
     * Returns all the cameras stored in the map.
     * @return ArrayList<Camera> of all the cameras.
     */
    public List<Camera> getCameras();

    /**
     * Returns all the players stored in the map.
     * @return ArrayList<Player> of all the cameras.
     */
    public List<Player> getPlayers();

    /**
     * Returns the width of this map.
     * @return int of the width.
     */
    public int getWidth();

    /**
     * Returns the height of the map.
     * @return int of the height.
     */
    public int getHeight();

    /**
     * Returns the background used for this map.
     * @return Image of the map.
     */
    public Image getBackground();

    /**
     * Adds a camera to the list of cameras in the map.
     * @param camera the Camera to add.
     */
    public void addCamera(Camera camera);

    /**
     * Adds a player to this list of players in the map.
     * @param player the Player to add.
     */
    public void addPlayer(Player player);
}
