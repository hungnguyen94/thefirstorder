package nl.tudelft.thefirstorder.domain;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author hung
 */
public class CueTest {

    private Cue cue;

    @Mock
    private Player player;

    @Mock
    private Script script;

    @Mock
    private Camera camera;

    @Mock
    private Project project;

    @Before
    public void setUp() throws Exception {
        cue = new Cue();
    }

    @Test
    public void idTest() throws Exception {
        Long cueId = 1L;
        cue.setId(cueId);
        assertThat(cue.getId()).isEqualTo(cueId);
    }

    @Test
    public void cameraTest() throws Exception {
        cue.setCamera(camera);
        assertThat(cue.getCamera()).isEqualTo(camera);
    }

    @Test
    public void playerTest() throws Exception {
        cue.setPlayer(player);
        assertThat(cue.getCamera()).isEqualTo(player);
    }

    @Test
    public void getActionTest() throws Exception {
        cue.setAction("Zoom");
        assertThat(cue.getAction()).isEqualTo("Zoom");
    }

    @Test
    public void getBarTest() throws Exception {
        cue.setBar(1);
        assertThat(cue.getBar()).isEqualTo(1);
    }

    @Test
    public void scriptTest() throws Exception {
        cue.setScript(script);
        assertThat(cue.getScript()).isEqualTo(script);
    }

    @Test
    public void projectTest() throws Exception {
        cue.setProject(project);
        assertThat(cue.getProject()).isEqualTo(project);
    }

    @Test
    public void getDurationTest() throws Exception {
        cue.setDuration(1);
        assertThat(cue.getDuration()).isEqualTo(1);
    }

    @Test
    public void equalsBranch1() throws Exception {
        assertThat(cue.equals(cue)).isTrue();
    }

    @Test
    public void equalsBranch2() throws Exception {
        assertThat(cue.equals(null)).isFalse();
    }

    @Test
    public void equalsBranch3() throws Exception {
        assertThat(cue.equals(new Object())).isFalse();
    }

    @Test
    public void equalsBranch4() throws Exception {
        Cue cue2 = new Cue();
        cue2.setId(cue.getId());
        assertThat(cue.equals(cue2)).isTrue();
    }

    @Test
    public void hashCodeTest() throws Exception {
        Cue cue2 = new Cue();
        cue.setId(1L);
        cue2.setId(1L);
        assertThat(cue.hashCode() == cue2.hashCode());
    }
}
