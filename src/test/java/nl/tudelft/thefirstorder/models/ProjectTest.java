
package nl.tudelft.thefirstorder.models;

import nl.tudelft.thefirstorder.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import nl.tudelft.thefirstorder.models.Script;
import nl.tudelft.thefirstorder.models.Map;
import static org.junit.Assert.assertEquals;

/**
 * Created by Martin on 4-5-2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectTest {

    @Mock private Script script;
    @Mock private Map map;
    @Mock private Script script2;
    @Mock private Map map2;
    private Project project;

    @Before
    public void setUp() {
        project = new Project(script,map);
    }

    @Test
    public void getSetScript() {
        project.setScript(script2);
        assertEquals(project.getScript(), script2);
    }

    @Test
    public void getSetMap() {
        project.setMap(map2);
        assertEquals(project.getMap(), map2);
    }
}
