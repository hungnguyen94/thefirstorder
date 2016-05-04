package nl.tudelft.thefirstorder.models;

/**
 * Class which represents a Project.
 * Created by Martin on 26-4-2016.
 */
public class Project {

    private Script script;
    private Map map;

    /**
     * Constructs a project.
     * @param script the script of the project
     * @param map the map of the project
     */
    public Project(Script script, Map map) {
        this.script = script;
        this.map = map;
    }

    /**
     * Get the script of the project.
     * @return the script
     */
    public Script getScript() {
        return script;
    }

    /**
     * Get the map of the project.
     * @return the map
     */
    public Map getMap() {
        return map;
    }

    /**
     * Set the script of the project.
     * @param script the script
     */
    public void setScript(Script script) {
        this.script = script;
    }

    /**
     * Set the map of the project.
     * @param map the map
     */
    public void setMap(Map map) {
        this.map = map;
    }

}


