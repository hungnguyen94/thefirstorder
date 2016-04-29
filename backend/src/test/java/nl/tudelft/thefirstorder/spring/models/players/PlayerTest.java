package nl.tudelft.thefirstorder.spring.models.players;

import org.junit.Test;

/**
 * Created by rubenwiersma on 29/04/16.
 */
public abstract class PlayerTest {
    protected Player player;
    protected int x;
    protected int y;
    protected String name;

    @Test
    public void PlayerTest() {
        assert(player.getX() == x);
        assert(player.getY() == y);
        assert(player.getName().equals(name));
    }
}
