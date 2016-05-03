package nl.tudelft.thefirstorder.models;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Martin on 22-4-2016.
 */
public class ScriptTest {

    @Mock private Cue cue1;
    @Mock private Cue cue2;
    private Script script = new Script(new ArrayList<Cue>());

    @Before
    public void setUp() {
        ArrayList<Cue> list = new ArrayList<Cue>();
        list.add(cue1);
        script = new Script(list);
    }

    @Test
    public void addLastTest() {
        script.addLast(cue2);
        ArrayList<Cue> list = new ArrayList<Cue>();
        list.add(cue1);
        list.add(cue2);
        assertEquals(list,script.getCues());
    }

    @Test
    public void addFirstTest() {
        script.addFirst(cue2);
        ArrayList<Cue> list = new ArrayList<Cue>();
        list.add(cue2);
        list.add(cue1);
        assertEquals(list,script.getCues());
    }

    @Test
    public void removeExist() {
        script.addFirst(cue2);
        script.removeCue(cue1);
        ArrayList<Cue> list = new ArrayList<Cue>();
        list.add(cue2);
        assertEquals(list, script.getCues());
    }

    @Test
    public void removeAll() {
        script.addFirst(cue2);
        script.removeAllCues();
        ArrayList<Cue> list = new ArrayList<Cue>();
        assertEquals(list, script.getCues());
    }

}
