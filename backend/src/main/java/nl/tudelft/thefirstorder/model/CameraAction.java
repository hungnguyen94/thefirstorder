package nl.tudelft.thefirstorder.model;

/**
 * The CameraAction class represents an action that can be made by a Camera.
 */
public abstract class CameraAction {
    private String name;
    private int duration;

    /**
     * Constructor for the CameraAction class.
     * @param name String of the name of the action
     * @param duration int of the duration of the action
     */
    public CameraAction(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }
}
