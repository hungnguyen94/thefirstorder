package nl.tudelft.thefirstorder.model.cameras;

import nl.tudelft.thefirstorder.model.CameraAction;

import java.util.Set;

/**
 * Created by rubenwiersma on 22/04/16.
 */
public abstract class Camera {
    public String name;
    public int x;
    public int y;
    public Set<CameraAction> actions;

    public Camera(String name, int x, int y, Set<CameraAction> actions) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.actions = actions;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Set<CameraAction> getActions() {
        return actions;
    }

    public boolean hasAction(CameraAction action) {
        return actions.contains(action);
    }

    public void addAction(CameraAction action) {
        actions.add(action);
    }
}
