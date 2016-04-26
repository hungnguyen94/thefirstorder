package nl.tudelft.thefirstorder.model.players;

/**
 * The Player class represents a player and his/her location on the map.
 */
public abstract class Player {
    public String name;
    public int x;
    public int y;

    /**
     * Constructor for the Player class.
     * @param name String of the name of the instrument.
     * @param x int the x position.
     * @param y int the y position.
     */
    public Player(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the name of the player.
     * @return String name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the x position of the player.
     * @return int x position.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y position of the player.
     * @return int y position.
     */
    public int getY() {
        return y;
    }
}
