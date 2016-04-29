package nl.tudelft.thefirstorder.model;

/**
 * Class which represents a Cue.
 * Created by Martin on 22-4-2016.
 */
public class Cue {

    private Camera camera;
    private Player player;
    private CameraAction cameraAction;
    private Time time;

    /**
     * Create a cue.
     * @param camera The camera of the cue
     * @param player The instrument of the cue
     * @param cameraAction The action for the cue
     * @param time The duration of the cue
     */
    public Cue(Camera camera, Player player, CameraAction cameraAction, Time time) {
        this.camera = camera;
        this.player = player;
        this.cameraAction = cameraAction;
        this.time = time;
    }

    /**
     * Get the camera of the cue.
     * @return the camera
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * Get the instrument of the cue.
     * @return the instrument
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get the action of the cue.
     * @return the action
     */
    public CameraAction getCameraAction() {
        return cameraAction;
    }

    /**
     * Get the duration of the cue.
     * @return the duration
     */
    public Time getTime() {
        return time;
    }

    /**
     * Change the camera of the cue.
     * @param camera the new camera
     */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Change the instrument of the cue.
     * @param player the new instrument
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Change the action of the cue.
     * @param cameraAction the new action
     */
    public void setCameraAction(CameraAction cameraAction) {
        this.cameraAction = cameraAction;
    }

    /**
     * Change the duration of the cue.
     * @param time the new duration
     */
    public void setTime(Time time) {
        this.time = time;
    }

}
