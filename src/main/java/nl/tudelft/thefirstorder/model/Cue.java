package nl.tudelft.thefirstorder.model;

/**
 * Class which represents a Cue.
 * Created by Martin on 22-4-2016.
 */
public class Cue {

    private Camera camera;
    private Instrument instrument;
    private Action action;
    private Time time;

    /**
     * Create a cue.
     * @param camera The camera of the cue
     * @param intstrument The instrument of the cue
     * @param action The action for the cue
     * @param time The duration of the cue
     */
    public Cue(Camera camera, Instrument intstrument, Action action, Time time) {
        this.camera = camera;
        this.instrument = intstrument;
        this.action = action;
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
    public Instrument getInstrument() {
        return instrument;
    }

    /**
     * Get the action of the cue.
     * @return the action
     */
    public Action getAction() {
        return action;
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
     * @param instrument the new instrument
     */
    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    /**
     * Change the action of the cue.
     * @param action the new action
     */
    public void setAction(Action action) {
        this.action = action;
    }

    /**
     * Change the duration of the cue.
     * @param time the new duration
     */
    public void setTime(Time time) {
        this.time = time;
    }

}
