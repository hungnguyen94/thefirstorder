package nl.tudelft.thefirstorder.model;

import nl.tudelft.thefirstorder.model.cameras.Camera;
import nl.tudelft.thefirstorder.model.players.Player;

import java.util.ArrayList;

/**
 * The ConcertMap class represents a map for a standard concert.
 */
public class ConcertMap implements Map {
    private final ArrayList<Camera> cameras;
    private final ArrayList<Player> players;
    private final int width;
    private final int height;
    private final Image background;

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
     * @return ArrayList<Camera> of all the cameras.
     */
    public ArrayList<Camera> getCameras() {
        return cameras;
    }

    /**
     * Returns all the players stored in the map.
     * @return ArrayList<Player> of all the cameras.
     */
    public ArrayList<Player> getPlayers() {
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
        cameras.add(camera);
    }

    /**
     * Adds a player to this list of players in the map.
     * @param player the Player to add.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * The ConcertMapBuilder builds a ConcertMap.
     */
    public static class ConcertMapBuilder {
        private ArrayList<Camera> cameras;
        private ArrayList<Player> players;
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
         * Builds a ConcertMap object
         * @return ConcertMap object
         */
        public ConcertMap build() {
            return new ConcertMap(this);
        }
    }
}
