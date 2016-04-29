package nl.tudelft.thefirstorder.spring.models.cameras;

import nl.tudelft.thefirstorder.spring.models.actions.CameraAction;

import java.util.Set;

/**
 * Represents a camera in the map.
 */
public abstract class Camera {
    public String name;
    public int x;
    public int y;
    public Set<CameraAction> actions;

    /**
     * Constructor for the Camera class.
     * @param name String name of the camera.
     * @param x int x position.
     * @param y int y position.
     * @param actions Set<CameraAction> a set of all CameraActions.
     */
    public Camera(String name, int x, int y, Set<CameraAction> actions) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.actions = actions;
    }

    /**
     * Returns the name of the camera.
     * @return String name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the x position of the camera.
     * @return int x position.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y position of the camera.
     * @return int y position.
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the actions of the camera.
     * @return Set<CameraAction> of all CameraActions.
     */
    public Set<CameraAction> getActions() {
        return actions;
    }

    /**
     * Returns wether a camera can perform a certain action.
     * @param action CameraAction to check for.
     * @return boolean.
     */
    public boolean hasAction(CameraAction action) {
        return actions.contains(action);
    }

    /**
     * Adds an CameraAction to this camera.
     * @param action the CameraAction to add.
     */
    public void addAction(CameraAction action) {
        actions.add(action);
    }
}
