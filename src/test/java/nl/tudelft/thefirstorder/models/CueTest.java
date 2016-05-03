package nl.tudelft.thefirstorder.models;

import nl.tudelft.thefirstorder.models.cameras.Camera;
import nl.tudelft.thefirstorder.models.actions.CameraAction;
import nl.tudelft.thefirstorder.models.players.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by Martin on 22-4-2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class CueTest {

    @Mock private Camera camera;
    @Mock private Player player;
    @Mock private CameraAction cameraAction;
    @Mock private Time time;
    private Cue cue;

    @Before
    public void SetUp() {
        cue = new Cue(null,null,null,null);
    }

    @Test
    public void getSetCamera() {
        cue.setCamera(camera);
        assertEquals(cue.getCamera(),camera);
    }

    @Test
    public void getSetInstrument() {
        cue.setPlayer(player);
        assertEquals(cue.getPlayer(),player);
    }

    @Test
    public void getSetCameraAction() {
        cue.setCameraAction(cameraAction);
        assertEquals(cue.getCameraAction(),cameraAction);
    }

    @Test
    public void getSetTime() {
        cue.setTime(time);
        assertEquals(cue.getTime(),time);
    }

}
