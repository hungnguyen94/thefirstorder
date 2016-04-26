package nl.tudelft.thefirstorder.model.players;

import java.util.Set;

/**
 * Created by rubenwiersma on 22/04/16.
 */
public abstract class Player {
    public String name;
    public int x;
    public int y;

    public Player(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
