package nl.tudelft.thefirstorder.model.cameras;

import nl.tudelft.thefirstorder.model.CameraAction;

import java.util.Set;

/**
 * Created by rubenwiersma on 22/04/16.
 */
public class PanasonicCamera extends Camera {

    public PanasonicCamera(String name, int x, int y, Set<CameraAction> actions) {
        super(name, x, y, actions);
    }

}
