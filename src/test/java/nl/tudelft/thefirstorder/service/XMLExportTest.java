package nl.tudelft.thefirstorder.service;
import nl.tudelft.thefirstorder.domain.Camera;
import nl.tudelft.thefirstorder.domain.Cue;
import nl.tudelft.thefirstorder.domain.Player;
import nl.tudelft.thefirstorder.domain.Project;
import nl.tudelft.thefirstorder.domain.Script;
import nl.tudelft.thefirstorder.service.util.XMLExportUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Class which tests the XML Export.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class XMLExportTest {

    @Mock private Project project;
    @Mock private Cue cue;
    @Mock private Document document;
    @Mock private Element element1;
    @Mock private Element element2;
    @Mock private Element element3;
    @Mock private Element element4;
    @Mock private Element special;
    @Mock private Text text;
    @Mock private Camera camera;
    @Mock private Player player;
    @Mock private Script script;

    @Test
    public void exportProjectToXMLTest(){
        XMLExportUtil util = new XMLExportUtil();
        when(cue.getCamera()).thenReturn(camera);
        when(camera.getId()).thenReturn(new Long(1));
        when(camera.getName()).thenReturn("Camera");
        when(camera.getX()).thenReturn(1D);
        when(camera.getY()).thenReturn(1D);
        when(document.createElement(any(String.class))).thenReturn(special);
        when(document.createTextNode(any(String.class))).thenReturn(text);
        when(cue.getPlayer()).thenReturn(player);
        when(player.getId()).thenReturn(new Long(1));
        when(player.getName()).thenReturn("Player");
        when(player.getX()).thenReturn(1D);
        when(player.getY()).thenReturn(1D);
        when(project.getName()).thenReturn("Project");
        when(project.getScript()).thenReturn(script);
        when(script.getName()).thenReturn("Script");
        Set<Cue> cues = new HashSet<Cue>();
        cues.add(cue);
        when(script.getCues()).thenReturn(cues);
        XMLExportUtil.exportProjectToXML(project);
    }

}
