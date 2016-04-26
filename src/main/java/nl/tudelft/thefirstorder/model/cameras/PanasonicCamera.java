package nl.tudelft.thefirstorder.model.cameras;


import java.util.HashSet;

/**
 * A standard Panasonic camera.
 */
public class PanasonicCamera extends Camera {

    /**
     * Constructor for the PanasonicCamera class.
     * @param name String name of the camera.
     * @param x int x position.
     * @param y int y position.
     */
    public PanasonicCamera(String name, int x, int y) {
        super(name, x, y, new HashSet<>());
    }

}
