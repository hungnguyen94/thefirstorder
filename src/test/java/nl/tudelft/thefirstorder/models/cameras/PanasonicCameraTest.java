package nl.tudelft.thefirstorder.models.cameras;

import nl.tudelft.thefirstorder.models.actions.PanAction;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by rubenwiersma on 29/04/16.
 */
public class PanasonicCameraTest extends CameraTest {
    @Before
    public void setup() {
        name = "p1";
        x = 2;
        y = 3;
        action = new PanAction();
        camera = new PanasonicCamera(name, x, y);
    }

    @Test
    public void PanasonicCameraTest() {
        super.CameraTest();
    }

    @Test
    public void addActionTest() {
        super.addActionTest();
    }
}
