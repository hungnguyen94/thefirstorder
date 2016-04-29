package nl.tudelft.thefirstorder.spring.models;

import nl.tudelft.thefirstorder.spring.models.cameras.Camera;
import nl.tudelft.thefirstorder.spring.models.cameras.PanasonicCamera;
import nl.tudelft.thefirstorder.spring.models.players.Player;
import nl.tudelft.thefirstorder.spring.models.players.PianoPlayer;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

/**
 * Created by rubenwiersma on 26/04/16.
 */
public class ConcertMapTest {

    private ConcertMap.ConcertMapBuilder builder;

    @Before
    public void setup() {
        builder = new ConcertMap.ConcertMapBuilder(20, 30);
        builder.background("/img/map1.jpg");
    }

    @Test
    public void ConcertMapTest() {
        builder.cameras(new ArrayList<>());
        builder.players(new ArrayList<>());
        ConcertMap map = builder.build();
        assert(map != null);
        assert(map.getWidth() == 20);
        assert(map.getHeight() == 30);
        assert(map.getBackground().getUrl().equals("/img/map1.jpg"));
        assert(map.getCameras().equals(new ArrayList<>()));
        assert(map.getPlayers().equals(new ArrayList<>()));
    }

    @Test
    public void addCameraTest() {
        ConcertMap map = builder.build();
        Camera c1 = new PanasonicCamera("c1", 2, 3);
        Camera c2 = new PanasonicCamera("c2", 2, 3);
        map.addCamera(c1);
        assert(map.getCameras().size() == 1);
        map.addCamera(c2);
        assert(map.getCameras().size() == 2);
    }

    @Test
    public void addCameraTestFail() {
        ConcertMap map = builder.build();
        Camera c1 = new PanasonicCamera("c1", 25, 35);
        map.addCamera(c1);
        assert(map.getCameras().size() == 0);
    }

    @Test
    public void addPlayerTest() {
        ConcertMap map = builder.build();
        Player p1 = new PianoPlayer(2, 3);
        Player p2 = new PianoPlayer(2, 3);
        map.addPlayer(p1);
        assert(map.getPlayers().size() == 1);
        map.addPlayer(p2);
        assert(map.getPlayers().size() == 2);
    }

    @Test
    public void addPlayerTestFail() {
        ConcertMap map = builder.build();
        Player p1 = new PianoPlayer(25, 35);
        map.addPlayer(p1);
        assert(map.getPlayers().size() == 0);
    }
}
