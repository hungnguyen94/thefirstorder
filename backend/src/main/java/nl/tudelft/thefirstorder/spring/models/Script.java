package nl.tudelft.thefirstorder.spring.models;

import java.util.LinkedList;
import java.util.List;

/**
 * Class which represents a Script.
 * Created by Martin on 22-4-2016.
 */
public class Script {

    private List<Cue> cues;

    /**
     * Create a Script.
     * @param cues The cues which are already in the script
     */
    public Script(List<Cue> cues) {
        this.cues = cues;
    }

    /**
     * Get the list of cues.
     * @return the list of cues
     */
    public List<Cue> getCues() {
        return cues;
    }

    /**
     * Add a cue to the end of the script.
     * @param cue the cue which has to be added
     */
    public void addLast(Cue cue) {
        cues.add(cue);
    }

    /**
     * Add a cue to the begin of the script.
     * @param cue the cue which has to be added
     */
    public void addFirst(Cue cue) {
        cues.add(0,cue);
    }

    /**
     * Remove a cue from the script.
     * @param cue the cue which has to be removed
     */
    public void removeCue(Cue cue) {
        cues.remove(cue);
    }

    public void removeAllCues() {
        cues = new LinkedList<Cue>();
    }

    /**
     * Add a cue in after a particular cue.
     * @param cueBefore the cue after which one has to be added
     * @param cueAfter the cue which has to be added
     */
    public void addAfter(Cue cueBefore, Cue cueAfter) {
        List<Cue> result = new LinkedList<Cue>();
        int i = cues.indexOf(cueBefore);
        if(i == -1) {
            addFirst(cueAfter);
        } else {
            cues.add(i, cueAfter);
        }
    }

}
