package nl.tudelft.thefirstorder.models;

import nl.tudelft.thefirstorder.models.cameras.Camera;
import nl.tudelft.thefirstorder.models.players.Player;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * The ConcertMap class represents a map for a standard concert.
 */
public class ConcertMap implements Map {
    private final List<Camera> cameras;
    private final List<Player> players;
    private final int width;
    private final int height;
    private final Image background;
    public static final Logger log = Logger.getLogger(ConcertMap.class);

    /**
     * Constructor for the ConcertMap class.
     * @param builder a ConcertMapBuilder object that builds a ConcertMap.
     */
    public ConcertMap(ConcertMapBuilder builder) {
        cameras = builder.cameras;
        players = builder.players;
        width = builder.width;
        height = builder.height;
        background = builder.background;
    }

    /**
     * Returns all the cameras stored in the map.
     * @return List of all the cameras.
     */
    public List<Camera> getCameras() {
        return cameras;
    }

    /**
     * Returns all the players stored in the map.
     * @return List of all the cameras.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the width of this map.
     * @return int of the width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the map.
     * @return int of the height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the background used for this map.
     * @return Image of the map.
     */
    public Image getBackground() {
        return background;
    }

    /**
     * Adds a camera to the list of cameras in the map.
     * @param camera the Camera to add.
     */
    public void addCamera(Camera camera) {
        if (camera.getX() < width && camera.getY() < height) {
            cameras.add(camera);
        } else {
            log.error("Camera " + camera.getName() + " not added, because the x and y coordinates were out of bounds.");
        }
    }

    /**
     * Adds a player to this list of players in the map.
     * @param player the Player to add.
     */
    public void addPlayer(Player player) {
        if (player.getX() < width && player.getY() < height) {
            players.add(player);
        } else {
            log.error("Player " + player.getName() + " not added, because the x and y coordinates were out of bounds.");
        }
    }

    /**
     * The ConcertMapBuilder builds a ConcertMap.
     */
    public static class ConcertMapBuilder {
        private List<Camera> cameras;
        private List<Player> players;
        private int width;
        private int height;
        private Image background;

        /**
         * Constructor for the ConcertMapBuilder.
         * @param width the width of the Map to build.
         * @param height the height of the Map to build.
         */
        public ConcertMapBuilder(int width, int height) {
            this.width = width;
            this.height = height;
            this.cameras = new ArrayList<>();
            this.players = new ArrayList<>();
        }

        /**
         * Sets a background for the ConcertMap.
         * @param url a String of the url of the image.
         * @return ConcertMapBuilder of the current object.
         */
        public ConcertMapBuilder background(String url) {
            this.background = new Image(url);
            return this;
        }

        /**
         * Sets a background for the ConcertMap.
         * @param cameras a list of cameras to intialize with.
         * @return ConcertMapBuilder of the current object.
         */
        public ConcertMapBuilder cameras(List<Camera> cameras) {
            this.cameras = cameras;
            return this;
        }

        /**
         * Sets a background for the ConcertMap.
         * @param players a list of players to intialize with.
         * @return ConcertMapBuilder of the current object.
         */
        public ConcertMapBuilder players(List<Player> players) {
            this.players = players;
            return this;
        }

        /**
         * Builds a ConcertMap object.
         * @return ConcertMap object
         */
        public ConcertMap build() {
            return new ConcertMap(this);
        }
    }
}
