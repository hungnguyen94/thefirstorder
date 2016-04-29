package nl.tudelft.thefirstorder.spring.models.cameras;

import nl.tudelft.thefirstorder.spring.models.actions.CameraAction;
import org.junit.Test;

/**
 * Created by rubenwiersma on 29/04/16.
 */
public abstract class CameraTest {
    protected Camera camera;
    protected int x;
    protected int y;
    protected String name;
    protected CameraAction action;

    @Test
    public void CameraTest() {
        assert(camera.getX() == x);
        assert(camera.getY() == y);
        assert(camera.getName().equals(name));
    }

    @Test
    public void addActionTest() {
        camera.addAction(action);
        assert(camera.getActions().size() == 1);
        camera.addAction(action);
        assert(camera.getActions().size() == 1);
        assert(camera.hasAction(action));
    }
}
