package nl.tudelft.thefirstorder.model;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;

/**
 * Created by Martin on 22-4-2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class CueTest {

    @Mock private Camera camera;
    @Mock private Instrument instrument;
    @Mock private Action action;
    @Mock private Time time;
    private Cue cue;

    @Before
    public void SetUp() {
        cue = new Cue(null,null,null,null);
    }

    @Test
    public void getSetCamera() {
        cue.setCamera(camera);
        assertEquals(cue.getCamera(),camera);
    }

    @Test
    public void getSetInstrument() {
        cue.setInstrument(instrument);
        assertEquals(cue.getInstrument(),instrument);
    }

    @Test
    public void getSetAction() {
        cue.setAction(action);
        assertEquals(cue.getAction(),action);
    }

    @Test
    public void getSetTime() {
        cue.setTime(time);
        assertEquals(cue.getTime(),time);
    }

}
