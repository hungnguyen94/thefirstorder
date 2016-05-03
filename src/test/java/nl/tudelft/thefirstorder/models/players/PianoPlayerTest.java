package nl.tudelft.thefirstorder.models.players;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by rubenwiersma on 29/04/16.
 */
public class PianoPlayerTest extends PlayerTest {
    @Before
    public void setup() {
        name = "Piano";
        x = 2;
        y = 3;
        player = new PianoPlayer(x, y);
    }

    @Test
    public void PanasonicCameraTest() {
        super.PlayerTest();
    }
}
