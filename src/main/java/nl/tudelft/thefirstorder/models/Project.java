package nl.tudelft.thefirstorder.models;

/**
 * Class which represents a Project.
 * Created by Martin on 26-4-2016.
 */
public class Project {

    private Script script;
    private Map map;

    public Project(Script script, Map map) {
        this.script = script;
        this.map = map;
    }

    public Script getScript() {
        return script;
    }

    public Map getMap() {
        return map;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public void setMap(Map map) {
        this.map = map;
    }

}


